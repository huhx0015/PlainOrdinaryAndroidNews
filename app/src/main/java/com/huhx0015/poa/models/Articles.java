package com.huhx0015.poa.models;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Articles {

    private static final String LOG_TAG = Articles.class.getSimpleName();

    private String mStatus;
    private String mSource;
    private String mSortBy;
    private List<Article> mArticles = null;

    public Articles(JSONObject jsonObject) {

        try {
            String status = jsonObject.getString("status");
            String source = jsonObject.getString("source");
            String sortBy = jsonObject.getString("sortBy");

            setStatus(status);
            setSource(source);
            setSortBy(sortBy);

            JSONArray articleArray = jsonObject.getJSONArray("articles");
            mArticles = new ArrayList<>();
            for (int index = 0; index < articleArray.length(); index++) {
                mArticles.add(new Article(articleArray.getJSONObject(index)));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "ERROR: An error occurred while deserializing JSON object.");
            e.printStackTrace();
        }
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        this.mStatus = status;
    }

    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        this.mSource = source;
    }

    public String getSortBy() {
        return mSortBy;
    }

    public void setSortBy(String sortBy) {
        this.mSortBy = sortBy;
    }

    public List<Article> getArticles() {
        return mArticles;
    }

    public void setArticles(List<Article> articles) {
        this.mArticles = articles;
    }

}
