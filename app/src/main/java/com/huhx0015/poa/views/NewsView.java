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
import com.huhx0015.poa.network.ApiClient;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Michael Yoon Huh on 4/4/2017.
 */

public class NewsView implements NewsResponseListener {

    private static final int NEWS_THREAD_TIMER = 100;

    private static final String LOG_TAG = NewsView.class.getSimpleName();
    private static final String TYPE_ARTICLES = "ARTICLES";
    private static final String TYPE_SOURCES = "SOURCES";

    private Activity mActivity;
    private Articles mNewsArticles;
    private ListView mNewsListView;
    private View mNewsView;
    private ViewStub mNewsViewStub;

    public NewsView(Activity activity) {
        this.mActivity = activity;
    }

    public void inflateView() {
        if (mNewsViewStub != null) {
            mNewsViewStub.setLayoutResource(R.layout.view_news);
            mNewsView = mNewsViewStub.inflate();

            mNewsListView = (ListView) mNewsView.findViewById(R.id.news_listview);
        }
    }

    public void initListView() {
        final ArticleAdapter articleAdapter = new ArticleAdapter(mActivity, mNewsArticles.getArticles());

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mNewsListView.setAdapter(articleAdapter);
            }
        });

    }

    public void setViewStub(ViewStub viewStub) {
        this.mNewsViewStub = viewStub;
    }

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
                ApiClient newsApiClient = new ApiClient(url);
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
                Log.d(LOG_TAG, "News Request Thread Status: " + newsThread.isAlive());

                if (!newsThread.isAlive()) {
                    threadHandler.removeCallbacks(this);
                } else {
                    threadHandler.postDelayed(this, NEWS_THREAD_TIMER);
                }
            }
        };
        threadHandler.postDelayed(networkThreadCheck, NEWS_THREAD_TIMER);
    }

    @Override
    public void onNewsGetResponse(String response, String type) {

        switch (type) {

            case TYPE_ARTICLES:
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    mNewsArticles = new Articles(jsonObject);
                    initListView();
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "ERROR: An exception occurred while converting response to a JSON object.");
                    e.printStackTrace();
                }
                break;
        }
    }
}
