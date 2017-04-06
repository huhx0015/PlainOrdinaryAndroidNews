package com.huhx0015.poa.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.huhx0015.poa.R;
import com.huhx0015.poa.interfaces.NewsActionListener;
import com.huhx0015.poa.models.Source;
import com.huhx0015.poa.network.ImageLoader;

/**
 * Created by Michael Yoon Huh on 4/5/2017.
 */

public class SourceView extends RelativeLayout {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // VIEW VARIABLES:
    private ImageView mLogoImage;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public SourceView(Context context) {
        super(context);
        initView(context);
    }

    public SourceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SourceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SourceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    /** INITIALIZATION METHODS _________________________________________________________________ **/

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View sourceView = inflater.inflate(R.layout.view_source_children, this, true);
        mLogoImage = (ImageView) sourceView.findViewById(R.id.view_source_logo);
    }

    public static SourceView inflate(ViewGroup parent) {
        SourceView sourceView = (SourceView) LayoutInflater.from(parent.getContext()).inflate(R.layout.view_source, parent, false);
        return sourceView;
    }

    /** SET METHODS ____________________________________________________________________________ **/

    public void setSource(final Source source, final NewsActionListener listener, Activity activity) {
        if (source != null) {

            // LOGO IMAGE:
            if (source.getUrlsToLogos() != null && source.getUrlsToLogos().getMedium() != null) {
                mLogoImage.setImageBitmap(null);
                ImageLoader.loadImage(mLogoImage, source.getUrlsToLogos().getMedium(), activity);
            }

            // LOGO LISTENER:
            if (source.getId() != null) {
                getRootView().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onNewsSourceSelected(source.getId());
                    }
                });
            }
        }
    }
}