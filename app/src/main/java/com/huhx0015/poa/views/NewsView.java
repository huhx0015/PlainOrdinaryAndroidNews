package com.huhx0015.poa.views;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ListView;
import com.huhx0015.poa.R;
import com.huhx0015.poa.adapters.ArticleAdapter;
import com.huhx0015.poa.constants.AppConstants;
import com.huhx0015.poa.interfaces.NewsResponseListener;
import com.huhx0015.poa.models.Articles;
import com.huhx0015.poa.network.HttpClient;
import com.huhx0015.poa.utils.JsonUtils;
import com.huhx0015.poa.utils.RecycleUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Michael Yoon Huh on 4/4/2017.
 */

public class NewsView implements NewsResponseListener {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // ACTIVITY VARIABLES:
    private Activity mActivity;

    // CONSTANT VARIABLES:
    private static final int NEWS_THREAD_TIMER = 100;
    private static final String TYPE_ARTICLES = "ARTICLES";
    private static final String TYPE_SOURCES = "SOURCES";

    // DATA VARIABLES:
    private Articles mNewsArticles;

    // LOGGING VARIABLES:
    private static final String LOG_TAG = NewsView.class.getSimpleName();

    // VIEW VARIABLES:
    private ListView mNewsListView;
    private View mNewsView;
    private ViewStub mNewsViewStub;

    /** CONSTRUCTOR METHOD _____________________________________________________________________ **/

    public NewsView(ViewStub viewStub, Activity activity) {
        this.mNewsViewStub = viewStub;
        this.mActivity = activity;
    }

    /** VIEW METHODS ___________________________________________________________________________ **/

    public void inflateView() {
        if (mNewsViewStub != null) {
            mNewsViewStub.setLayoutResource(R.layout.view_news);
            mNewsView = mNewsViewStub.inflate();

            mNewsListView = (ListView) mNewsView.findViewById(R.id.news_listview);
        }
    }

    private void initListView() {
        if (mNewsArticles != null && mNewsArticles.getArticles() != null && mNewsArticles.getArticles().size() > 0) {
            final ArticleAdapter articleAdapter = new ArticleAdapter(mActivity, mNewsArticles.getArticles());

            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mNewsListView.setAdapter(articleAdapter);
                }
            });
        }
    }

    /** HTTP METHODS ___________________________________________________________________________ **/

    public void requestNews(String query, Context context) {
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
        final Handler threadHandler = new Handler();

        final Thread newsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient newsApiClient = new HttpClient(url);
                try {
                    newsApiClient.sendGet(NewsView.this, type);
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
                    initListView();
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "ERROR: An exception occurred while converting response to a JSON object.");
                    e.printStackTrace();
                }
                break;
        }
    }

    /** RECYCLE METHODS ________________________________________________________________________ **/

    public void unbindView() {
        RecycleUtils.unbindViews(mNewsView.findViewById(R.id.view_news_layout));
    }
}
