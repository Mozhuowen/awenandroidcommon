package com.mozhuowen.rxandroid.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.mozhuowen.rxandroid.ui.LMRecyclerView;
import com.mozhuowen.rxandroid.ui.ListView;

import butterknife.ButterKnife;

/**
 * Created by Awen on 16/5/17.
 * Email:mozhuowen@gmail.com
 */
public abstract class BaseListActivity extends BaseActivity implements ListView,
        LMRecyclerView.LoadMoreListener,SwipeRefreshLayout.OnRefreshListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,getLayoutResId());

        ButterKnife.bind(this);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public abstract String getTitleString();
    protected abstract int getLayoutResId();
    protected abstract void initPresenter();
    public abstract boolean isActionbarVisible();
    public abstract boolean isDisplayHomeAsUpEnabled();
}
