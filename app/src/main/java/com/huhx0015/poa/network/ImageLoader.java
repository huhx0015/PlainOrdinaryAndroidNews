package com.huhx0015.poa.network;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Michael Yoon Huh on 4/5/2017.
 */

public class ImageLoader {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // CONSTANTS VARIABLES:
    private static final int IMAGE_THREAD_TIMER = 100;
    private static final String LOG_TAG = ImageLoader.class.getSimpleName();

    /** IMAGE LOADING METHODS __________________________________________________________________ **/

    public synchronized static void loadImage(final ImageView image, String imageUrl, final Activity activity) {
        try {
            final URL url = new URL(imageUrl);
            final Handler threadHandler = new Handler();

            final Thread imageThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        retrieveImage(image, url, activity);
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "ERROR: Exception encountered: " + e.getLocalizedMessage());
                        e.printStackTrace();
                    }
                }
            });
            imageThread.start();

            Runnable threadCheck = new Runnable() {
                @Override
                public void run() {
                    Log.d(LOG_TAG, "Image Thread Status: " + imageThread.isAlive());

                    if (!imageThread.isAlive()) {
                        threadHandler.removeCallbacks(this);
                    } else {
                        threadHandler.postDelayed(this, IMAGE_THREAD_TIMER);
                    }
                }
            };
            threadHandler.postDelayed(threadCheck, IMAGE_THREAD_TIMER);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "ERROR: Exception encountered: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private static void retrieveImage(final ImageView image, URL url, Activity activity) {
        try {
            final Bitmap bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
            if (bitmap != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image.setImageBitmap(bitmap);
                    }
                });
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "ERROR: " + e.getLocalizedMessage());
        }
    }
}