package com.mozhuowen.rxandroidframework.ui.activity;

import com.mozhuowen.rxandroid.activity.NavigationTabsActivity;
import com.mozhuowen.rxandroid.ui.handlers.ViewPagerHandler;
import com.mozhuowen.widget.material.fragment.BlankFragment;

/**
 * Created by Awen on 16/6/20.
 * Email:mozhuowen@gmail.com
 */
public class TestNavigationTab extends NavigationTabsActivity {

    @Override
    public String getTitleString() {
        return "test";
    }

    @Override
    public boolean isDisplayHomeAsUpEnabled() {
        return false;
    }

    @Override
    protected void initSelfViews() {

    }

    @Override
    protected boolean expandTabs() {
        return false;
    }

    @Override
    protected int getTextColor() {
        return getResources().getColor(android.R.color.white);
    }

    @Override
    protected int getTabsTextPiexlSize() {
        return 35;
    }

    @Override
    protected int getTextSelectedColor() {
        return getResources().getColor(android.R.color.white);
    }

    @Override
    protected int getIndicatorColor() {
        return getResources().getColor(android.R.color.white);
    }

    @Override
    protected boolean enableActionBarShadow() {
        return false;
    }

    @Override
    protected boolean enableBackArrow() {
        return false;
    }

    @Override
    public ViewPagerHandler getViewPagerHandler() {
        ViewPagerHandler handler = new ViewPagerHandler(this)
                .addPage("test1", BlankFragment.newInstance("test1"))
                .addPage("test2", BlankFragment.newInstance("test2"))
                .addPage("test3",BlankFragment.newInstance("test3"));
        return handler;
    }

    @Override
    public int defaultViewPagerPageSelectedPosition() {
        return 0;
    }
}
