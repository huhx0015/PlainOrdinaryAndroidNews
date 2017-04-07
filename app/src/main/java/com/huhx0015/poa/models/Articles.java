package com.huhx0015.poa.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Articles implements Parcelable {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // LOGGING VARIABLES:
    private static final String LOG_TAG = Articles.class.getSimpleName();

    // MODEL VARIABLES:
    private String mStatus;
    private String mSource;
    private String mSortBy;
    private List<Article> mArticles = null;

    /** CLASS VARIABLES ________________________________________________________________________ **/

    public Articles(JSONObject jsonObject) {

        try {

            String status = jsonObject.getString("status");
            String source = jsonObject.getString("source");
            String sortBy = jsonObject.getString("sortBy");

            setStatus(status);
            setSource(source);
            setSortBy(sortBy);

            JSONArray articleArray = jsonObject.getJSONArray("articles");
            List<Article> articleList = new ArrayList<>();
            for (int index = 0; index < articleArray.length(); index++) {
                articleList.add(new Article(articleArray.getJSONObject(index)));
            }
            setArticles(articleList);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "ERROR: An error occurred while deserializing JSON object.");
            e.printStackTrace();
        }
    }

    /** PARCELABLE METHODS _____________________________________________________________________ **/

    protected Articles(Parcel in) {
        mStatus = in.readString();
        mSource = in.readString();
        mSortBy = in.readString();
        mArticles = in.createTypedArrayList(Article.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mStatus);
        dest.writeString(mSource);
        dest.writeString(mSortBy);
        dest.writeTypedList(mArticles);
    }

    public static final Creator<Articles> CREATOR = new Creator<Articles>() {
        @Override
        public Articles createFromParcel(Parcel in) {
            return new Articles(in);
        }

        @Override
        public Articles[] newArray(int size) {
            return new Articles[size];
        }
    };

    /** GET METHODS ____________________________________________________________________________ **/

    public String getStatus() {
        return mStatus;
    }

    public String getSource() {
        return mSource;
    }

    public String getSortBy() {
        return mSortBy;
    }

    public List<Article> getArticles() {
        return mArticles;
    }

    /** SET METHODS ____________________________________________________________________________ **/

    public void setStatus(String status) {
        this.mStatus = status;
    }

    public void setSource(String source) {
        this.mSource = source;
    }

    public void setSortBy(String sortBy) {
        this.mSortBy = sortBy;
    }

    public void setArticles(List<Article> articles) {
        this.mArticles = articles;
    }
}