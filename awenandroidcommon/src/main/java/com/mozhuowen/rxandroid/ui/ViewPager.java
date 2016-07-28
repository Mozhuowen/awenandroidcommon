package com.mozhuowen.rxandroid.ui;

import com.mozhuowen.rxandroid.ui.handlers.ViewPagerHandler;

/**
 * Created by Awen on 16/6/20.
 * Email:mozhuowen@gmail.com
 */
public interface ViewPager {

    ViewPagerHandler getViewPagerHandler();

    void selectPage(int position);

    void setOnPageChangeListener(
            android.support.v4.view.ViewPager.OnPageChangeListener onPageChangeListener);

    void updateNavigationDrawerTopHandler(ViewPagerHandler viewPagerHandler,
                                          int defaultViewPagerPageSelectedPosition);

    int defaultViewPagerPageSelectedPosition();

}
