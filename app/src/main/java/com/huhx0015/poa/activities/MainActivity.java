package com.huhx0015.poa.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.huhx0015.poa.views.NewsView;
import com.huhx0015.poa.R;
import com.huhx0015.poa.utils.VersionUtils;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends Activity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private int mApiLevel = 1;
    private ListView mListView;
    private ViewStub mViewStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApiLevel = VersionUtils.getApiLevel();

        initView();
        initListView();

        initNews();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.main_listview);
        mViewStub = (ViewStub) findViewById(R.id.main_viewstub);
    }

    private void initListView() {
        List<String> itemList = new LinkedList<>();
        itemList.add("Item 1");
        itemList.add("Item 2");

        ArrayAdapter<String> nodeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemList);

        mListView.setAdapter(nodeAdapter);
    }

    private void initNews() {
        NewsView newsView = new NewsView(this);
        newsView.setViewStub(mViewStub);
        newsView.inflateView();

        newsView.requestNews("the-next-web", this);
        //newsView.requestSources();
    }


    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    class NetworkDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @TargetApi(Build.VERSION_CODES.CUPCAKE)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @TargetApi(Build.VERSION_CODES.CUPCAKE)
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }
    }
}
