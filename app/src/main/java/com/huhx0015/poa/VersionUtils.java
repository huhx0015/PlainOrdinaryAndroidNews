package com.huhx0015.poa;

import android.annotation.TargetApi;
import android.os.Build;

/**
 * Created by Michael Yoon Huh on 4/4/2017.
 */

public class VersionUtils {

    @TargetApi(Build.VERSION_CODES.DONUT)
    public static int getApiLevel() {
        return Build.VERSION.SDK_INT;
    }
}