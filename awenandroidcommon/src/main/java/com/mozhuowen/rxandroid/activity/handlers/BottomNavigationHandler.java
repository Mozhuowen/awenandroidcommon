package com.mozhuowen.rxandroid.activity.handlers;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Awen on 16/5/19.
 * Email:mozhuowen@gmail.com
 */
public class BottomNavigationHandler {

    private Context mContext;
    private List<String> titles;
    private List<Fragment> fragments;

    public BottomNavigationHandler(Context context) {
        this.mContext = context;
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
    }

    public BottomNavigationHandler addFragment(Fragment f) {
        fragments.add(f);
        return this;
    }

    public BottomNavigationHandler addTitle(String title) {
        this.titles.add(title);
        return this;
    }

    public List<Fragment> getFragments() {
        return this.fragments;
    }
}
