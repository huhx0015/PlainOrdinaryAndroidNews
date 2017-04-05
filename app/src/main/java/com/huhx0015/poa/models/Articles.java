package com.huhx0015.poa.models;

import java.util.List;

public class Articles {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    private String mStatus;
    private String mSource;
    private String mSortBy;
    private List<Article> mArticles = null;

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
