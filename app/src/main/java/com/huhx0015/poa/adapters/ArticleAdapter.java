package com.huhx0015.poa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.huhx0015.poa.R;
import com.huhx0015.poa.models.Article;
import java.util.List;

/**
 * Created by Michael Yoon Huh on 4/5/2017.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {

    public ArticleAdapter(Context context, List<Article> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Article article = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_article_item, parent, false);
        }

        // Lookup view for data population
        TextView articleTitle = (TextView) convertView.findViewById(R.id.adapter_article_title);
        TextView articleDescription = (TextView) convertView.findViewById(R.id.adapter_article_description);

        // Populate the data into the template view using the data object
        if (article != null) {

            if (article.getTitle() != null) {
                articleTitle.setText(article.getTitle());
            }

            if (article.getDescription() != null) {
                articleDescription.setText(article.getDescription());
            }
        }

        // Return the completed view to render on screen
        return convertView;
    }
}