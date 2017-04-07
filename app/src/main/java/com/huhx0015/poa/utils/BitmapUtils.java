package com.huhx0015.poa.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by Michael Yoon Huh on 4/7/2017.
 */

public class BitmapUtils {

    /** BITMAP METHODS _________________________________________________________________________ **/

    // getResizedBitmap(): Generates a resized bitmap with the specified width and height.
    // Source: http://stackoverflow.com/questions/4837715/how-to-resize-a-bitmap-in-android
    public static Bitmap getResizedBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        bitmap.recycle();
        return resizedBitmap;
    }
}
