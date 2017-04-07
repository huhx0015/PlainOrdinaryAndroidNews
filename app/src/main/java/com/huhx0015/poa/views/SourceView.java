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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.huhx0015.poa.R;
import com.huhx0015.poa.interfaces.ImageLoadListener;
import com.huhx0015.poa.interfaces.NewsActionListener;
import com.huhx0015.poa.models.Source;
import com.huhx0015.poa.network.ImageLoader;

/**
 * Created by Michael Yoon Huh on 4/5/2017.
 */

public class SourceView extends RelativeLayout implements ImageLoadListener {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // LIST VARIABLES:
    private int mPosition;

    // VIEW VARIABLES:
    private ImageView mLogoImage;
    private ProgressBar mProgressBar;

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
        mProgressBar = (ProgressBar) sourceView.findViewById(R.id.view_source_progress_bar);
    }

    public static SourceView inflate(ViewGroup parent) {
        SourceView sourceView = (SourceView) LayoutInflater.from(parent.getContext()).inflate(R.layout.view_source, parent, false);
        return sourceView;
    }

    /** GET METHODS ____________________________________________________________________________ **/

    public int getPosition() {
        return mPosition;
    }

    /** SET METHODS ____________________________________________________________________________ **/

    public void setSource(final Source source, final NewsActionListener listener, int position, Activity activity) {
        this.mPosition = position;

        if (source != null) {

            // LOGO IMAGE:
            if (source.getUrlsToLogos() != null && source.getUrlsToLogos().getMedium() != null) {
                mLogoImage.setImageBitmap(null);
                ImageLoader.loadImage(mLogoImage, source.getUrlsToLogos().getMedium(), false, this, activity);
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

    /** INTERFACE METHODS ______________________________________________________________________ **/

    @Override
    public void onImageLoaded() {
        mProgressBar.setVisibility(View.GONE);
    }
}