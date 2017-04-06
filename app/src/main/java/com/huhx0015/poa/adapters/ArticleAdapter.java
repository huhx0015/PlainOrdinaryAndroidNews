package com.huhx0015.poa.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.huhx0015.poa.models.Article;
import com.huhx0015.poa.views.ArticleView;
import java.util.List;

/**
 * Created by Michael Yoon Huh on 4/5/2017.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    private Activity mActivity;

    /** CONSTRUCTOR METHOD _____________________________________________________________________ **/

    public ArticleAdapter(List<Article> articles, Activity activity) {
        super(activity, 0, articles);

        this.mActivity = activity;
    }

    /** ADAPTER METHODS ________________________________________________________________________ **/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ArticleView articleView = (ArticleView) convertView;

        if (null == articleView) {
            articleView = ArticleView.inflate(parent);
        }
        articleView.setArticle(getItem(position), mActivity);

        return articleView;
    }
}