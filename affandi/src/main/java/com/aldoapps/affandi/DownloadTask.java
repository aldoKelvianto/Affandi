package com.aldoapps.affandi;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by aldo on 02/11/17.
 */

class DownloadTask implements Runnable {

  public static final int MAX_BUFFER = 1024;
  public static final int WHAT = 1;
  public static final String KEY_BITMAP_ARRAY = "key_bitmap_array";
  public static final String KEY_URL = "key_bitmap_url";

  private Handler bitmapHandler;
  private String imageUrl;

  public DownloadTask(Handler bitmapHandler, String imageUrl) {
    this.bitmapHandler = bitmapHandler;
    this.imageUrl = imageUrl;
  }

  @Override public void run() {
    byte[] result;

    try {
      URL url = new URL(imageUrl);
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      InputStream inputStream = urlConnection.getInputStream();

      ByteArrayOutputStream out = new ByteArrayOutputStream();

      if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
        throw new IOException("Failed to make a connection");
      }

      int bytesRead;
      byte[] buffer = new byte[MAX_BUFFER];
      while ((bytesRead = inputStream.read(buffer)) > 0) {
        out.write(buffer, 0, bytesRead);
      }

      result = out.toByteArray();
      out.close();
      urlConnection.disconnect();

      Message message = bitmapHandler.obtainMessage();
      message.what = WHAT;
      message.obj = result;
      message.getData().putByteArray(KEY_BITMAP_ARRAY, result);
      message.getData().putString(KEY_URL, imageUrl);
      message.sendToTarget();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
