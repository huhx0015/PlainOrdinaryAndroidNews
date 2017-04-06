package com.huhx0015.poa.interfaces;

import com.huhx0015.poa.models.Sources;

/**
 * Created by Michael Yoon Huh on 4/5/2017.
 */

public interface NewsActionListener {
    void onNewsSourceDownloaded(Sources source);
    void onNewsSourceSelected(String source);
}
