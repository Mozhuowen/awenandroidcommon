package com.mozhuowen.rxandroid.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.mozhuowen.rxandroid.ui.BaseView;

/**
 * Created by Awen on 16/5/18.
 * Email:mozhuowen@gmail.com
 */
public abstract class NormalActivity extends BaseActivity implements BaseView,SwipeRefreshLayout.OnRefreshListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,getLayoutResId());

        ActionBar actionBar = getSupportActionBar();
        if (isActionbarVisible()) {
            actionBar.setTitle(getTitleString());
            actionBar.setDisplayHomeAsUpEnabled(isDisplayHomeAsUpEnabled());
        } else
            actionBar.hide();

        initPresenter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return true;
        }
    }

    public abstract String getTitleString();
    protected abstract int getLayoutResId();
    protected abstract void initPresenter();
    public abstract boolean isActionbarVisible();
    public abstract boolean isDisplayHomeAsUpEnabled();
}
