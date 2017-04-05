package com.huhx0015.poa.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Michael Yoon Huh on 4/4/2017.
 */

public class Toolbar extends RelativeLayout {

    public Toolbar(Context context) {
        super(context);
        initView();
    }

    public Toolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public Toolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Toolbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {

    }
}
