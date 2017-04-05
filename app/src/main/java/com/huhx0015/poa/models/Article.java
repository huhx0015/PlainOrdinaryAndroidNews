package com.huhx0015.poa.models;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class Article {

    private static final String LOG_TAG = Article.class.getSimpleName();

    private String mAuthor;
    private String mTitle;
    private String mDescription;
    private String mUrl;
    private String mUrlToImage;
    private String mPublishedAt;

    public Article(JSONObject jsonObject) {
        try {
            String author = jsonObject.getString("author");
            String title = jsonObject.getString("title");
            String description = jsonObject.getString("description");
            String url = jsonObject.getString("url");
            String urlToImage = jsonObject.getString("urlToImage");
            String publishedAt = jsonObject.getString("publishedAt");

            setAuthor(author);
            setTitle(title);
            setDescription(description);
            setUrl(url);
            setUrlToImage(urlToImage);
            setPublishedAt(publishedAt);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "ERROR: An error occurred while deserializing JSON object.");
            e.printStackTrace();
        }
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        this.mAuthor = author;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getUrlToImage() {
        return mUrlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.mUrlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return mPublishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.mPublishedAt = publishedAt;
    }
}
