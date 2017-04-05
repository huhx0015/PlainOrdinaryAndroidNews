package com.huhx0015.poa.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huhx0015.poa.R;

/**
 * Created by Michael Yoon Huh on 4/4/2017.
 */

public class Toolbar extends RelativeLayout {

    private ImageButton mActionButton;
    private TextView mToolbarTitle;

    public Toolbar(Context context) {
        super(context);
        initView(context);
    }

    public Toolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public Toolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Toolbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toolbarView = inflater.inflate(R.layout.view_toolbar, this, true);

        mActionButton = (ImageButton) toolbarView.findViewById(R.id.view_action_button);
        mToolbarTitle = (TextView) toolbarView.findViewById(R.id.view_toolbar_title);
    }

    public void setToolbarTitle(String name) {
        mToolbarTitle.setText(name);
    }
}
