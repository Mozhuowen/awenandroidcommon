package com.mozhuowen.rxandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mozhuowen.rxandroid.model.ViewPagerItem;

import java.util.List;

/**
 * Created by Awen on 16/6/20.
 * Email:mozhuowen@gmail.com
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<ViewPagerItem> mViewPagerItems;

    public ViewPagerAdapter(FragmentManager fm, List<ViewPagerItem> viewPagerItems) {
        super(fm);

        mViewPagerItems = viewPagerItems;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mViewPagerItems.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return mViewPagerItems.size();
    }

    @Override
    public Fragment getItem(int position) {
//        Logger.d("Got fragment position->"+position);
        return mViewPagerItems.get(position).getFragment();
    }

}
