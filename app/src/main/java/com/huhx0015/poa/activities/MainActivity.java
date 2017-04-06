package com.huhx0015.poa.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import com.huhx0015.poa.interfaces.NewsActionListener;
import com.huhx0015.poa.utils.DialogUtils;
import com.huhx0015.poa.utils.RecycleUtils;
import com.huhx0015.poa.stubviews.NewsStubView;
import com.huhx0015.poa.R;
import com.huhx0015.poa.views.Toolbar;

/**
 * Created by Michael Yoon Huh on 4/4/2017.
 */

public class MainActivity extends Activity implements NewsActionListener {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // DATA VARIABLES:
    private String mCurrentSource;

    // LOGGING VARIABLES:
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    // VIEW VARIABLES:
    private boolean isNewsVisible = false;
    private NewsStubView mNewsView;
    private RelativeLayout mViewStubContainer;
    private Toolbar mToolbar;

    /** ACTIVITY LIFECYCLE METHODS _____________________________________________________________ **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkIfApiKeyExists()) {
            initView();
            initSources();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RecycleUtils.unbindViews(findViewById(R.id.main_activity_layout));
    }

    /** ACTIVITY EXTENSION METHODS _____________________________________________________________ **/

    @Override
    public void onBackPressed() {
        if (isNewsVisible) {
            removeNews();
            initSources();
        } else {
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /** INTERFACE METHODS ______________________________________________________________________ **/

    @Override
    public void onNewsSourceSelected(String source) {
        Log.d(LOG_TAG, "News Source Selected: " + source);
        removeNews();
        initNews(source);
    }

    /** LAYOUT METHODS _________________________________________________________________________ **/

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mViewStubContainer = (RelativeLayout) findViewById(R.id.main_viewstub_container);
    }

    private void initSources() {
        ViewStub viewStub = new ViewStub(this);
        mViewStubContainer.addView(viewStub);

        mNewsView = new NewsStubView(viewStub, this, this);
        mNewsView.inflateView();
        mNewsView.requestSources();

        mToolbar.setToolbarActionVisibility(false);
        mToolbar.setToolbarActionListener(null);
    }

    private void initNews(String source) {
        this.mCurrentSource = source;

        ViewStub viewStub = new ViewStub(this);
        mViewStubContainer.addView(viewStub);

        mNewsView = new NewsStubView(viewStub, this, this);
        mNewsView.inflateView();
        mNewsView.requestNews(mCurrentSource, this);
        isNewsVisible = true;

        mToolbar.setToolbarActionVisibility(true);
        mToolbar.setToolbarActionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeNews();
                initSources();
            }
        });
    }

    private void removeNews() {
        mNewsView.deflate();
        mNewsView = null;
        mCurrentSource = null;
        isNewsVisible = false;

        mToolbar.setToolbarActionVisibility(false);
        mToolbar.setToolbarActionListener(null);
        mViewStubContainer.removeAllViews();
    }

    /** MISCELLANEOUS METHODS __________________________________________________________________ **/

    private boolean checkIfApiKeyExists() {
        String newsApiKey = getString(R.string.news_api_key);

        if (newsApiKey.length() < 1) {
            DialogUtils.displayAlertDialog(getString(R.string.dialog_title_no_key_title),
                    getString(R.string.dialog_title_no_key_message), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }, this);
            return false;
        } else {
            return true;
        }
    }
}
