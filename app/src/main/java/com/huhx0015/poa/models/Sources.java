package com.huhx0015.poa.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class Sources implements Parcelable {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    private String mStatus;
    private List<Source> mSources = null;

    /** PARCELABLE METHODS _____________________________________________________________________ **/

    protected Sources(Parcel in) {
        mStatus = in.readString();
        mSources = in.createTypedArrayList(Source.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mStatus);
        dest.writeTypedList(mSources);
    }

    public static final Creator<Sources> CREATOR = new Creator<Sources>() {
        @Override
        public Sources createFromParcel(Parcel in) {
            return new Sources(in);
        }

        @Override
        public Sources[] newArray(int size) {
            return new Sources[size];
        }
    };

    /** GET / SET METHODS ______________________________________________________________________ **/

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        this.mStatus = status;
    }

    public List<Source> getSources() {
        return mSources;
    }

    public void setSources(List<Source> sources) {
        this.mSources = sources;
    }
}