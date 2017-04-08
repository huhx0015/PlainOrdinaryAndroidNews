package com.huhx0015.poa.images;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import com.huhx0015.poa.interfaces.ImageLoadListener;
import com.huhx0015.poa.utils.BitmapUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Michael Yoon Huh on 4/5/2017.
 */

public class ImageLoader {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // LOGGING VARIABLES:
    private static final String LOG_TAG = ImageLoader.class.getSimpleName();

    /** IMAGE LOADING METHODS __________________________________________________________________ **/

    public synchronized static void loadImage(final ImageView image, String imageUrl,
                                              final boolean isScaled, final ImageLoadListener listener,
                                              final Activity activity) {
        try {
            final URL url = new URL(imageUrl);

            final Thread imageThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        retrieveImage(image, url, isScaled, listener, activity);
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "ERROR: Exception encountered: " + e.getLocalizedMessage());
                        e.printStackTrace();
                    }
                }
            });
            imageThread.start();
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "ERROR: Exception encountered: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private static void retrieveImage(final ImageView image, URL url, boolean isScaled,
                                      final ImageLoadListener listener, Activity activity) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
            if (isScaled) {
                bitmap = BitmapUtils.getResizedBitmap(bitmap.copy(Bitmap.Config.RGB_565, false),
                        bitmap.getWidth() / 2, bitmap.getHeight() / 2);
            } else {
                bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
            }
            final Bitmap optimizedBitmap = bitmap;

            if (bitmap != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            listener.onImageLoaded();
                        }
                        image.setImageBitmap(optimizedBitmap);
                    }
                });
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "ERROR: " + e.getLocalizedMessage());
        }
    }
}