package com.mozhuowen.rxandroidframework.ui.activity;

import android.support.v4.view.ViewPager;

import com.mozhuowen.rxandroid.activity.NormalActivity;
import com.mozhuowen.rxandroid.ui.handlers.ActionBarHandler;
import com.mozhuowen.rxandroid.ui.handlers.ActionBarHandlerDefault;
import com.mozhuowen.rxandroidframework.R;
import com.mozhuowen.widget.views.BottomNavigation.view.BottomNavigationView;

import butterknife.Bind;

/**
 * Created by Awen on 16/5/18.
 * Email:mozhuowen@gmail.com
 */
public class BottomActivity extends NormalActivity {

    @Bind(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Override
    public String getTitleString() {
        return "BottomActivity";
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_navigation;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public boolean isActionbarVisible() {
        return true;
    }

    @Override
    public boolean isDisplayHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected boolean enableActionBarShadow() {
        return false;
    }

    @Override
    protected ActionBarHandler getActionBarHandler() {
        return new ActionBarHandlerDefault(this);
    }

    @Override
    protected boolean enableBackArrow() {
        return true;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void onRefresh() {

    }
}
