package com.huhx0015.poa.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.huhx0015.poa.utils.RecycleUtils;
import com.huhx0015.poa.stubs.NewsStub;
import com.huhx0015.poa.R;
import com.huhx0015.poa.views.Toolbar;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends Activity {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // LOGGING VARIABLES:
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    // VIEW VARIABLES:
    private ListView mListView;
    private Toolbar mToolbar;
    private ViewStub mViewStub;

    /** ACTIVITY LIFECYCLE METHODS _____________________________________________________________ **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListView();
        initNews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RecycleUtils.unbindViews(findViewById(R.id.main_activity_layout));
    }

    /** ACTIVITY EXTENSION METHODS _____________________________________________________________ **/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /** INITIALIZATION METHODS _________________________________________________________________ **/

    private void initView() {
        mListView = (ListView) findViewById(R.id.main_listview);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
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
        NewsStub newsView = new NewsStub(mViewStub, this);
        newsView.inflateView();

        newsView.requestNews("the-next-web", this);
        //newsView.requestSources();
    }
}
