package com.huhx0015.poa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UrlsToLogos implements Parcelable {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    private String mSmall;
    private String mMedium;
    private String mLarge;

    /** PARCELABLE METHODS _____________________________________________________________________ **/

    public UrlsToLogos() {}

    protected UrlsToLogos(Parcel in) {
        mSmall = in.readString();
        mMedium = in.readString();
        mLarge = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSmall);
        dest.writeString(mMedium);
        dest.writeString(mLarge);
    }

    public static final Creator<UrlsToLogos> CREATOR = new Creator<UrlsToLogos>() {
        @Override
        public UrlsToLogos createFromParcel(Parcel in) {
            return new UrlsToLogos(in);
        }

        @Override
        public UrlsToLogos[] newArray(int size) {
            return new UrlsToLogos[size];
        }
    };

    /** GET / SET METHODS ______________________________________________________________________ **/

    public String getSmall() {
        return mSmall;
    }

    public void setSmall(String small) {
        this.mSmall = small;
    }

    public String getMedium() {
        return mMedium;
    }

    public void setMedium(String medium) {
        this.mMedium = medium;
    }

    public String getLarge() {
        return mLarge;
    }

    public void setLarge(String large) {
        this.mLarge = large;
    }
}
