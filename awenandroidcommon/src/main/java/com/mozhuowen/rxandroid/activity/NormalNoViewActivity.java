package com.mozhuowen.rxandroid.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import butterknife.ButterKnife;

/**
 * Created by Awen on 16/7/7.
 * Email:mozhuowen@gmail.com
 */
public abstract class NormalNoViewActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,getLayoutResId());
        ButterKnife.bind(this);
        initPresenter();

        ActionBar actionBar = getSupportActionBar();
        if (isActionbarVisible()) {
            actionBar.setTitle(getTitleString());
            actionBar.setDisplayHomeAsUpEnabled(isDisplayHomeAsUpEnabled());
        } else
            actionBar.hide();

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
