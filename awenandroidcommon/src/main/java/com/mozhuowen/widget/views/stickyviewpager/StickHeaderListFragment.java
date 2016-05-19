package com.mozhuowen.widget.views.stickyviewpager;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mozhuowen.widget.views.stickyviewpager.scroll.ScrollFragment;


/**
 * Created by sj on 15/11/20.
 */
public abstract class StickHeaderListFragment extends ScrollFragment<ListView> {

    int layoutId;

    public StickHeaderListFragment() { }

    public StickHeaderListFragment(int layoutId) { this.layoutId = layoutId; }

    @Override
    public ListView createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView listView = layoutId > 0 ? (ListView) inflater.inflate(layoutId, container, false) : null;
        if (listView == null) {
            listView = new ListView(getActivity());
        }
        return listView;
    }
}
