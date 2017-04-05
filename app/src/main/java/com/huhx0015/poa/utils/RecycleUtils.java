package com.huhx0015.poa.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 * Created by Michael Yoon Huh on 4/5/2017.
 */

public class RecycleUtils {

    /** UNBIND METHODS _________________________________________________________________________ **/

    // unbindViews(): Unbinds all Drawable objects attached to the view layout by setting them
    // to null, freeing up memory resources and preventing Context-related memory leaks. This code
    // is borrowed from Roman Guy at www.curious-creature.org.
    public static void unbindViews(View view) {

        // If the View object's background is not null, a Callback is set to render them null.
        if (view.getBackground() != null) { view.getBackground().setCallback(null); }

        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {

            int i = 0;
            for (int x : new int[((ViewGroup) view).getChildCount()]) {
                unbindViews(((ViewGroup) view).getChildAt(i++));
            }

            ((ViewGroup) view).removeAllViews(); // Removes all View objects in the ViewGroup.
        }
    }
}
