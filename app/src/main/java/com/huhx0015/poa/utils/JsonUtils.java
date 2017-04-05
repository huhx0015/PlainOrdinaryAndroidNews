package com.huhx0015.poa.utils;

import android.util.Log;
import com.huhx0015.poa.models.Article;
import com.huhx0015.poa.models.Articles;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael Yoon Huh on 4/5/2017.
 */

public class JsonUtils {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // LOGGING VARIABLES:
    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    /** JSON PARSER METHODS ____________________________________________________________________ **/

    public static Articles articlesFromJson(JSONObject jsonObject) {
        Articles articles = new Articles();

        try {

            String status = jsonObject.getString("status");
            String source = jsonObject.getString("source");
            String sortBy = jsonObject.getString("sortBy");

            articles.setStatus(status);
            articles.setSource(source);
            articles.setSortBy(sortBy);

            JSONArray articleArray = jsonObject.getJSONArray("articles");
            List<Article> articleList = new ArrayList<>();
            for (int index = 0; index < articleArray.length(); index++) {
                articleList.add(articleFromJson(articleArray.getJSONObject(index)));
            }
            articles.setArticles(articleList);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "ERROR: An error occurred while deserializing JSON object.");
            e.printStackTrace();
        }

        return articles;
    }

    public static Article articleFromJson(JSONObject jsonObject) {
        Article article = new Article();

        try {
            String author = jsonObject.getString("author");
            String title = jsonObject.getString("title");
            String description = jsonObject.getString("description");
            String url = jsonObject.getString("url");
            String urlToImage = jsonObject.getString("urlToImage");
            String publishedAt = jsonObject.getString("publishedAt");

            article.setAuthor(author);
            article.setTitle(title);
            article.setDescription(description);
            article.setUrl(url);
            article.setUrlToImage(urlToImage);
            article.setPublishedAt(publishedAt);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "ERROR: An error occurred while deserializing JSON object.");
            e.printStackTrace();
        }

        return article;
    }
}
