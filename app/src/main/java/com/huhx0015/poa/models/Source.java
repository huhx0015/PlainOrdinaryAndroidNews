package com.huhx0015.poa.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Source implements Parcelable {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // LOGGING VARIABLES:
    private static final String LOG_TAG = Source.class.getSimpleName();

    // MODEL VARIABLES:
    private String mId;
    private String mName;
    private String mDescription;
    private String mUrl;
    private String mCategory;
    private String mLanguage;
    private String mCountry;
    private UrlsToLogos mUrlsToLogos;
    private List<String> mSortBysAvailable = null;

    /** CONSTRUCTOR METHOD _____________________________________________________________________ **/

    public Source(JSONObject jsonObject) {

        try {
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String description = jsonObject.getString("description");
            String url = jsonObject.getString("url");
            String category = jsonObject.getString("category");
            String language = jsonObject.getString("language");
            String country = jsonObject.getString("country");

            setId(id);
            setName(name);
            setDescription(description);
            setUrl(url);
            setCategory(category);
            setLanguage(language);
            setCountry(country);

            JSONObject urlsToLogoObject = jsonObject.getJSONObject("urlsToLogos");
            UrlsToLogos urlsToLogos = new UrlsToLogos(urlsToLogoObject);
            setUrlsToLogos(urlsToLogos);

            JSONArray sortBysAvailableArray = jsonObject.getJSONArray("sortBysAvailable");
            List<String> sortBysAvailableList = new ArrayList<>();
            for (int index = 0; index < sortBysAvailableArray.length(); index++) {
                sortBysAvailableList.add(sortBysAvailableArray.getString(index));
            }
            setSortBysAvailable(sortBysAvailableList);

        } catch (JSONException e) {
            Log.e(LOG_TAG, "ERROR: An error occurred while deserializing JSON object.");
            e.printStackTrace();
        }
    }

    /** PARCELABLE METHODS _____________________________________________________________________ **/

    protected Source(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mDescription = in.readString();
        mUrl = in.readString();
        mCategory = in.readString();
        mLanguage = in.readString();
        mCountry = in.readString();
        mUrlsToLogos = in.readParcelable(UrlsToLogos.class.getClassLoader());
        mSortBysAvailable = in.createStringArrayList();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeString(mUrl);
        dest.writeString(mCategory);
        dest.writeString(mLanguage);
        dest.writeString(mCountry);
        dest.writeParcelable(mUrlsToLogos, flags);
        dest.writeStringList(mSortBysAvailable);
    }

    public static final Creator<Source> CREATOR = new Creator<Source>() {
        @Override
        public Source createFromParcel(Parcel in) {
            return new Source(in);
        }

        @Override
        public Source[] newArray(int size) {
            return new Source[size];
        }
    };

    /** GET / SET METHODS ______________________________________________________________________ **/

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
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

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        this.mCategory = category;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        this.mLanguage = language;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        this.mCountry = country;
    }

    public UrlsToLogos getUrlsToLogos() {
        return mUrlsToLogos;
    }

    public void setUrlsToLogos(UrlsToLogos urlsToLogos) {
        this.mUrlsToLogos = urlsToLogos;
    }

    public List<String> getSortBysAvailable() {
        return mSortBysAvailable;
    }

    public void setSortBysAvailable(List<String> sortBysAvailable) {
        this.mSortBysAvailable = sortBysAvailable;
    }
}