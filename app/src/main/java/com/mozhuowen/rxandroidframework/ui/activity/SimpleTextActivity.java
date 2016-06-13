package com.mozhuowen.rxandroidframework.ui.activity;

import com.mozhuowen.rxandroid.activity.NormalActivity;
import com.mozhuowen.rxandroid.ui.handlers.ActionBarHandler;
import com.mozhuowen.rxandroid.ui.handlers.ActionBarHandlerDefault;
import com.mozhuowen.rxandroidframework.R;

/**
 * Created by Awen on 16/6/13.
 * Email:mozhuowen@gmail.com
 */
public class SimpleTextActivity extends NormalActivity {
    @Override
    public String getTitleString() {
        return "simleTestActivity";
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_testeve;
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
        return false;
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
        return false;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void onRefresh() {

    }
}
