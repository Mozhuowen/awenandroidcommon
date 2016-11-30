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

    private Bundle dataBundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,getLayoutResId());
        ButterKnife.bind(this);

        if ( savedInstanceState != null )
            dataBundle = savedInstanceState.getBundle("bundle");
        else{
            dataBundle = getIntent().getExtras();
        }
        initPresenter(dataBundle);

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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (dataBundle != null) {
            outState.putBundle("bundle",dataBundle);
        }

        super.onSaveInstanceState(outState);
    }

    public abstract String getTitleString();
    protected abstract int getLayoutResId();
    protected abstract void initPresenter(Bundle savedInstanceState);
    public abstract boolean isActionbarVisible();
    public abstract boolean isDisplayHomeAsUpEnabled();

}
