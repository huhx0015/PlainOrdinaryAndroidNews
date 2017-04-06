package com.huhx0015.poa.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Michael Yoon Huh on 4/5/2017.
 */

public class IntentUtils {

    /** INTENT METHODS _________________________________________________________________________ **/

    public static void launchBrowerIntent(String url, Activity activity) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(intent);
    }
}
