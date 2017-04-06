package com.huhx0015.poa.stubviews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ListView;
import android.widget.TextView;
import com.huhx0015.poa.R;
import com.huhx0015.poa.adapters.ArticleAdapter;
import com.huhx0015.poa.adapters.SourceAdapter;
import com.huhx0015.poa.constants.AppConstants;
import com.huhx0015.poa.interfaces.NewsActionListener;
import com.huhx0015.poa.interfaces.NewsResponseListener;
import com.huhx0015.poa.models.Articles;
import com.huhx0015.poa.models.Sources;
import com.huhx0015.poa.network.HttpClient;
import com.huhx0015.poa.utils.DialogUtils;
import com.huhx0015.poa.utils.JsonUtils;
import com.huhx0015.poa.utils.RecycleUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Michael Yoon Huh on 4/4/2017.
 */

public class NewsStubView implements NewsResponseListener {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // ACTIVITY VARIABLES:
    private Activity mActivity;

    // CONSTANT VARIABLES:
    private static final int NEWS_THREAD_TIMER = 100;
    private static final String TYPE_ARTICLES = "ARTICLES";
    private static final String TYPE_SOURCES = "SOURCES";

    // DATA VARIABLES:
    private Articles mNewsArticles;
    private Sources mSources;

    // LISTENER VARIABLES:
    private NewsActionListener mNewsActionListener;

    // LOGGING VARIABLES:
    private static final String LOG_TAG = NewsStubView.class.getSimpleName();

    // VIEW VARIABLES:
    private ListView mNewsListView;
    private ProgressDialog mProgressDialog;
    private TextView mNewsStatusText;
    private View mNewsView;
    private ViewStub mNewsViewStub;

    /** CONSTRUCTOR METHOD _____________________________________________________________________ **/

    public NewsStubView(ViewStub viewStub, NewsActionListener listener, Activity activity) {
        this.mNewsViewStub = viewStub;
        this.mNewsActionListener = listener;
        this.mActivity = activity;
    }

    /** VIEW METHODS ___________________________________________________________________________ **/

    public void inflateView() {
        if (mNewsViewStub != null) {
            mNewsViewStub.setLayoutResource(R.layout.view_news);
            mNewsView = mNewsViewStub.inflate();

            mNewsListView = (ListView) mNewsView.findViewById(R.id.news_listview);
            mNewsStatusText = (TextView) mNewsView.findViewById(R.id.news_status);
        }
    }

    private void initNewsView() {
        if (mNewsArticles != null && mNewsArticles.getArticles() != null && mNewsArticles.getArticles().size() > 0) {
            final ArticleAdapter articleAdapter = new ArticleAdapter(mNewsArticles.getArticles(), mActivity);

            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mNewsListView.setAdapter(articleAdapter);
                }
            });
        } else {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mNewsStatusText.setText(mActivity.getResources().getString(R.string.news_status_error));
                    mNewsStatusText.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    public void initSourceView() {
        if (mSources != null && mSources.getSources() != null && mSources.getSources().size() > 0) {
            final SourceAdapter sourceAdapter = new SourceAdapter(mSources.getSources(),
                    mNewsActionListener, mActivity);

            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mNewsListView.setAdapter(sourceAdapter);
                }
            });
        } else {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mNewsStatusText.setText(mActivity.getResources().getString(R.string.source_status_error));
                    mNewsStatusText.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    /** SET METHODS ____________________________________________________________________________ **/

    public void setSources(Sources source) {
        this.mSources = source;
    }

    /** HTTP METHODS ___________________________________________________________________________ **/

    public void requestNews(String query, Context context) {

        Log.d(LOG_TAG, "Query: " + query);

        String url = AppConstants.NEWS_API_BASE_URL + AppConstants.NEWS_API_ARTICLES +
                AppConstants.NEWS_API_SOURCE + query + AppConstants.NEWS_API_SORT_BY +
                AppConstants.NEWS_API_LATEST +  AppConstants.NEWS_API_KEY +
                context.getResources().getString(R.string.news_api_key);

        startRequest(url, TYPE_ARTICLES);
    }

    public void requestSources() {
        String url = AppConstants.NEWS_API_BASE_URL + AppConstants.NEWS_API_SOURCES;
        startRequest(url, TYPE_SOURCES);
    }

    private synchronized void startRequest(final String url, final String type) {
        mProgressDialog = DialogUtils.createProgressDialog(mActivity);

        final Handler threadHandler = new Handler();
        final Thread newsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient newsApiClient = new HttpClient(url);
                try {
                    newsApiClient.sendGet(NewsStubView.this, type);
                } catch (Exception e) {
                    Log.e(LOG_TAG, "ERROR: Exception encountered: " + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        });
        newsThread.start();

        Runnable networkThreadCheck = new Runnable() {
            @Override
            public void run() {
                Log.d(LOG_TAG, "Request Thread Status: " + newsThread.isAlive());

                if (!newsThread.isAlive()) {
                    threadHandler.removeCallbacks(this);
                    mProgressDialog.dismiss();
                } else {
                    threadHandler.postDelayed(this, NEWS_THREAD_TIMER);
                }
            }
        };
        threadHandler.postDelayed(networkThreadCheck, NEWS_THREAD_TIMER);
    }

    /** INTERFACE METHODS ______________________________________________________________________ **/

    @Override
    public void onNewsGetResponse(String response, String type) {
        switch (type) {

            case TYPE_ARTICLES:
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    mNewsArticles = JsonUtils.articlesFromJson(jsonObject);
                    initNewsView();
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "ERROR: An exception occurred while converting response to a JSON object.");
                    e.printStackTrace();
                }
                break;
            case TYPE_SOURCES:
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    mSources = JsonUtils.sourcesFromJson(jsonObject);
                    initSourceView();
                    mNewsActionListener.onNewsSourceDownloaded(mSources);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "ERROR: An exception occurred while converting response to a JSON object.");
                    e.printStackTrace();
                }
                break;
            default:
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mNewsStatusText.setText(mActivity.getResources().getString(R.string.news_api_error));
                        mNewsStatusText.setVisibility(View.VISIBLE);
                    }
                });
                break;
        }
    }

    /** RECYCLE METHODS ________________________________________________________________________ **/

    public void deflate() {
        RecycleUtils.unbindViews(mNewsView.findViewById(R.id.view_news_layout));
    }
}