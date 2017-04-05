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

    /** CLASS VARIABLES ________________________________________________________________________ **/

    private ImageButton mActionButton;
    private TextView mToolbarTitle;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

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

    /** INITIALIZATION METHODS _________________________________________________________________ **/

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toolbarView = inflater.inflate(R.layout.view_toolbar, this, true);

        mActionButton = (ImageButton) toolbarView.findViewById(R.id.view_action_button);
        mToolbarTitle = (TextView) toolbarView.findViewById(R.id.view_toolbar_title);
    }

    /** SET METHODS ____________________________________________________________________________ **/

    public void setToolbarTitle(String name) {
        mToolbarTitle.setText(name);
    }

    public void setToolbarActionVisibility(boolean isVisible) {
        if (isVisible) {
            mActionButton.setVisibility(View.VISIBLE);
        } else {
            mActionButton.setVisibility(View.GONE);
        }
    }

    public void setToolbarActionListener(OnClickListener listener) {
        mActionButton.setOnClickListener(listener);
    }
}
