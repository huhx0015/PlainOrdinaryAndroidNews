package com.huhx0015.poa;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

        final Handler threadHandler = new Handler();


        final Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                ApiClient client = new ApiClient(AppConstants.BASE_URL);
                try {
                    client.sendGet();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        Runnable networkThreadCheck = new Runnable() {
            @Override
            public void run() {
                Log.d(LOG_TAG, "Thread Status: " + thread.isAlive());
                if (!thread.isAlive()) {
                    threadHandler.removeCallbacks(this);
                } else {
                    threadHandler.postDelayed(this, 100);
                }
            }
        };

        threadHandler.postDelayed(networkThreadCheck, 100);
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
