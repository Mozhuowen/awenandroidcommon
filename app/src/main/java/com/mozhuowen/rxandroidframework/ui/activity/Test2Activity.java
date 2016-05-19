package com.mozhuowen.rxandroidframework.ui.activity;

import com.mozhuowen.rxandroid.activity.NormalActivity;
import com.mozhuowen.rxandroid.ui.handlers.ActionBarHandler;
import com.mozhuowen.rxandroid.ui.handlers.ActionBarHandlerDefault;
import com.mozhuowen.rxandroidframework.R;

/**
 * Created by Awen on 16/5/18.
 * Email:mozhuowen@gmail.com
 */
public class Test2Activity extends NormalActivity {
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

    @Override
    public String getTitleString() {
        return "Test2activity";
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main_test2;
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
}
