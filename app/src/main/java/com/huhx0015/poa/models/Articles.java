package com.huhx0015.poa.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class Articles implements Parcelable {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    private String mStatus;
    private String mSource;
    private String mSortBy;
    private List<Article> mArticles = null;

    /** PARCELABLE METHODS _____________________________________________________________________ **/

    public Articles() {}

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