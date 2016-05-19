package com.mozhuowen.rxandroid.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Awen on 16/5/19.
 * Email:mozhuowen@gmail.com
 */
public class BNAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private List<Fragment> fragments;

    public BNAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
        this.fragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }
}
