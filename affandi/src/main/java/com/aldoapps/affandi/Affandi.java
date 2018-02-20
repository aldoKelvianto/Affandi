package com.aldoapps.affandi;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by aldo on 02/11/17.
 */

public class Affandi {

    private static final String TAG = "com.affandi.debug";
    private static Affandi INSTANCE;
    private static boolean isInitialized;

    private ThreadPoolExecutor downloadPool;
    private ThreadPoolExecutor decodePool;

    private Handler bitmapHandler;
    private Handler uiHandler;

    private LruCache<String, Bitmap> memoryCache;
    private Map<ImageView, String> imageViewStringMap;
    private Set<ImageView> imageViewSet;

    private AffandiStudio affandiStudio;

    private Affandi() {
        downloadPool = ThreadPoolFactory.getInstance().createBackgroundPool();
        decodePool = ThreadPoolFactory.getInstance().createBackgroundPool();

        bitmapHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                processBitmapMessage(msg);
                return true;
            }
        });
        uiHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                processUiMessage(msg);
            }
        };

        imageViewSet = new HashSet<>();
        imageViewStringMap = new HashMap<>();

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1_024);
        final int cacheSize = maxMemory / 8;
        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1_024;
            }
        };
    }

    public static AffandiStudio with(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null");
        }

        if (!isInitialized) {
            initializeActivityCallback(context);
        }

        return new AffandiStudio();
    }

    private static void initializeActivityCallback(Context context) {
        Application application = null;
        try {
            application = ((Activity) context).getApplication();
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (application != null) {
            application.registerActivityLifecycleCallbacks(new DefaultLifecycleCallback());
            isInitialized = true;
        }
    }

    public void into(ImageView imageView) {
        final Bitmap bitmapFromMemCache = getBitmapFromMemCache(affandiStudio.imageUrl);
        if (bitmapFromMemCache != null) {
            Log.d(TAG, "url: " + affandiStudio.imageUrl + "from cache");
            imageView.setImageBitmap(bitmapFromMemCache);
        } else {
            Log.d(TAG, "url: " + affandiStudio.imageUrl + "from network");
            load(affandiStudio.imageUrl, imageView);
        }
    }

    private Bitmap getBitmapFromMemCache(String key) {
        return memoryCache.get(key);
    }

    static Affandi getInstance() {
        if (INSTANCE == null) {
            synchronized (Affandi.class) {
                INSTANCE = new Affandi();
            }
        }
        return INSTANCE;
    }

    private void processUiMessage(Message msg) {
        if (msg.what != DecodeTask.WHAT) return;

        Bitmap bitmap = msg.getData().getParcelable(DecodeTask.KEY_BITMAP);
        String url = msg.getData().getString(DecodeTask.KEY_URL);

        addBitmapToMemoryCache(url, bitmap);

        for (ImageView imageView : imageViewSet) {
            String latestUrl = imageViewStringMap.get(imageView);
            if (!TextUtils.isEmpty(latestUrl) && latestUrl.equalsIgnoreCase(url)) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    private void processBitmapMessage(Message msg) {
        if (msg.what != DownloadTask.WHAT) return;

        byte[] bitmapArray = (byte[]) msg.obj;
        String url = msg.getData().getString(DownloadTask.KEY_URL);
        decodePool.execute(new DecodeTask(uiHandler, url, bitmapArray));
    }

    private void load(String imageUrl, ImageView imageView) {
        imageViewStringMap.put(imageView, imageUrl);
        if (imageViewSet.contains(imageView)) {
            imageView.setImageResource(android.R.color.transparent);
        }
        imageViewSet.add(imageView);
        downloadPool.execute(new DownloadTask(bitmapHandler, imageUrl));
    }

    void setStudio(AffandiStudio affandiStudio) {
        this.affandiStudio = affandiStudio;
    }

    void clearImageViewReferences() {
        imageViewStringMap.clear();
        imageViewSet.clear();
    }

}
