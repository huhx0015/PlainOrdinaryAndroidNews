package com.huhx0015.poa.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.huhx0015.poa.interfaces.NewsActionListener;
import com.huhx0015.poa.models.Source;
import com.huhx0015.poa.views.SourceView;
import java.util.List;

/**
 * Created by Michael Yoon Huh on 4/5/2017.
 */

public class SourceAdapter extends ArrayAdapter<Source> {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // ACTIVITY VARIABLES:
    private Activity mActivity;

    // LISTENER VARIABLES:
    private NewsActionListener mListener;

    /** CONSTRUCTOR METHOD _____________________________________________________________________ **/

    public SourceAdapter(List<Source> sources, NewsActionListener listener, Activity activity) {
        super(activity, 0, sources);

        this.mListener = listener;
        this.mActivity = activity;
    }

    /** ADAPTER METHODS ________________________________________________________________________ **/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SourceView sourceView = (SourceView) convertView;

        if (null == sourceView || sourceView.getPosition() != position) {
            sourceView = SourceView.inflate(parent);
        }
        sourceView.setSource(getItem(position), mListener, position, mActivity);

        return sourceView;
    }
}
