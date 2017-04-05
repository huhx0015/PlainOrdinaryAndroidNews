package com.huhx0015.poa.models;

public class Article {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    private String mAuthor;
    private String mTitle;
    private String mDescription;
    private String mUrl;
    private String mUrlToImage;
    private String mPublishedAt;

    /** GET METHODS ____________________________________________________________________________ **/

    public String getAuthor() {
        return mAuthor;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getUrlToImage() {
        return mUrlToImage;
    }

    public String getPublishedAt() {
        return mPublishedAt;
    }

    /** SET METHODS ____________________________________________________________________________ **/

    public void setAuthor(String author) {
        this.mAuthor = author;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public void setUrlToImage(String urlToImage) {
        this.mUrlToImage = urlToImage;
    }

    public void setPublishedAt(String publishedAt) {
        this.mPublishedAt = publishedAt;
    }
}
