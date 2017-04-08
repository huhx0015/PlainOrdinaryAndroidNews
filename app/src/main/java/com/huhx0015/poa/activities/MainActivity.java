package com.huhx0015.poa.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import com.huhx0015.poa.interfaces.NewsActionListener;
import com.huhx0015.poa.models.Sources;
import com.huhx0015.poa.utils.DialogUtils;
import com.huhx0015.poa.utils.NetworkUtils;
import com.huhx0015.poa.utils.RecycleUtils;
import com.huhx0015.poa.stubviews.NewsStubView;
import com.huhx0015.poa.R;
import com.huhx0015.poa.views.ToolbarView;

/**
 * Created by Michael Yoon Huh on 4/4/2017.
 */

public class MainActivity extends Activity implements NewsActionListener {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // DATA VARIABLES:
    private Sources mSources;
    private String mCurrentSource;

    // LOGGING VARIABLES:
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    // PARCELABLE VARIABLES:
    private static final String PARCELABLE_NEWS_SOURCE = LOG_TAG + "_NEWS_SOURCE";
    private static final String PARCELABLE_NEWS_VISIBLE = LOG_TAG + "_NEWS_VISIBLE";
    private static final String PARCELABLE_SOURCES = LOG_TAG + "_SOURCES";

    // VIEW VARIABLES:
    private boolean mIsNewsVisible = false;
    private NewsStubView mNewsView;
    private RelativeLayout mViewStubContainer;
    private ToolbarView mToolbarView;

    /** ACTIVITY LIFECYCLE METHODS _____________________________________________________________ **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        if (savedInstanceState != null) {
            mCurrentSource = savedInstanceState.getString(PARCELABLE_NEWS_SOURCE);
            mIsNewsVisible = savedInstanceState.getBoolean(PARCELABLE_NEWS_VISIBLE);
            mSources = savedInstanceState.getParcelable(PARCELABLE_SOURCES);

            if (mIsNewsVisible) {
                initNews(mCurrentSource);
            } else {
                initSources();
            }
        } else if (checkIfApiKeyExists() && checkIfInternetConnectionExists()) {
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
        if (mIsNewsVisible) {
            removeViewStub();
            initSources();
        } else {
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PARCELABLE_NEWS_SOURCE, mCurrentSource);
        outState.putBoolean(PARCELABLE_NEWS_VISIBLE, mIsNewsVisible);
        outState.putParcelable(PARCELABLE_SOURCES, mSources);
    }

    /** INTERFACE METHODS ______________________________________________________________________ **/

    @Override
    public void onNewsSourceDownloaded(Sources source) {
        this.mSources = source;
    }

    @Override
    public void onNewsSourceSelected(String source) {
        Log.d(LOG_TAG, "News Source Selected: " + source);
        removeViewStub();
        initNews(source);
    }

    /** LAYOUT METHODS _________________________________________________________________________ **/

    private void initView() {
        mToolbarView = (ToolbarView) findViewById(R.id.main_toolbar);
        mViewStubContainer = (RelativeLayout) findViewById(R.id.main_viewstub_container);
    }

    private void initSources() {
        initViewStub();

        if (mSources != null) {
            mNewsView.setSources(mSources);
            mNewsView.initSourceView();
        } else {
            mNewsView.requestSources();
        }

        mToolbarView.setToolbarActionVisibility(false);
        mToolbarView.setToolbarActionListener(null);
    }

    private void initNews(String source) {
        this.mCurrentSource = source;
        initViewStub();

        mNewsView.requestNews(mCurrentSource, this);
        mIsNewsVisible = true;

        mToolbarView.setToolbarActionVisibility(true);
        mToolbarView.setToolbarActionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeViewStub();
                initSources();
            }
        });
    }

    private void initViewStub() {
        ViewStub viewStub = new ViewStub(this);
        mViewStubContainer.addView(viewStub);

        mNewsView = new NewsStubView(viewStub, this, this);
        mNewsView.inflateView();
    }

    private void removeViewStub() {
        mNewsView.deflate();
        mNewsView = null;
        mCurrentSource = null;
        mIsNewsVisible = false;

        mToolbarView.setToolbarActionVisibility(false);
        mToolbarView.setToolbarActionListener(null);
        mViewStubContainer.removeAllViews();
    }

    /** MISCELLANEOUS METHODS __________________________________________________________________ **/

    private boolean checkIfApiKeyExists() {
        String newsApiKey = getString(R.string.news_api_key);

        if (newsApiKey.length() < 1) {
            DialogUtils.displayAlertDialog(getString(R.string.dialog_title_no_key_title),
                    getString(R.string.dialog_title_no_key_message), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }, this);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkIfInternetConnectionExists() {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            DialogUtils.displayAlertDialog(getString(R.string.dialog_title_no_internet_title),
                    getString(R.string.dialog_title_no_internet_message), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }, this);
            return false;
        } else {
            return true;
        }
    }
}
