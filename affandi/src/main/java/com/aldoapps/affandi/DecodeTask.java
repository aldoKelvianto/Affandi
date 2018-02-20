package com.aldoapps.affandi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

/**
 * Created by aldo on 02/11/17.
 */

class DecodeTask implements Runnable {

  public static final int WHAT = 99;
  public static final String KEY_BITMAP = "KEY_BITMAP";
  public static final String KEY_URL = "key_url";

  private Handler uiHandler;
  private String imageUrl;
  private byte[] bitmapArray;

  public DecodeTask(Handler uiHandler, String imageUrl, byte[] bitmapArray) {
    this.uiHandler = uiHandler;
    this.imageUrl = imageUrl;
    this.bitmapArray = bitmapArray;
  }

  @Override public void run() {
    Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
    Message message = uiHandler.obtainMessage();
    message.what = WHAT;
    message.getData().putParcelable(KEY_BITMAP, bitmap);
    message.getData().putString(KEY_URL, imageUrl);
    message.sendToTarget();
  }
}
