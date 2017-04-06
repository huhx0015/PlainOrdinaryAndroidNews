package com.huhx0015.poa.models;

import java.util.List;

public class Sources {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    private String mStatus;
    private List<Source> mSources = null;

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
