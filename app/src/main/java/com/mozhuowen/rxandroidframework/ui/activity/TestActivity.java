package com.mozhuowen.rxandroidframework.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mozhuowen.rxandroid.activity.BaseListActivity;
import com.mozhuowen.rxandroid.ui.LMRecyclerView;
import com.mozhuowen.rxandroid.ui.handlers.ActionBarHandler;
import com.mozhuowen.rxandroid.ui.handlers.ActionBarHandlerDefault;
import com.mozhuowen.rxandroidframework.R;
import com.mozhuowen.rxandroidframework.presenter.TestPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Awen on 16/5/17.
 * Email:mozhuowen@gmail.com
 */
public class TestActivity extends BaseListActivity{

    TestPresenter presenter;
    boolean isRefresh = true;

    @Bind(R.id.recycler_view)
    LMRecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.default_loading)
    ProgressBar full_progressbar;
    @Bind(R.id.error_view)
    View error_view;

    @Override
    public String getTitleString() {
        return "TestActivity";
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main_test;
    }

    @Override
    protected void initPresenter() {
        presenter = new TestPresenter(this,  this);
        presenter.init();
        presenter.setRecyclerView(recyclerView);
        presenter.setLoadMoreListener(this);

        presenter.fetchNextPage();
    }

    @Override
    public boolean isActionbarVisible() {
        return false;
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
        return false;
    }

    @Override
    public void refreshList() {

    }

    @Override
    public void showLoadingView() {
        error_view.setVisibility(View.GONE);
        full_progressbar.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView() {
        presenter.getAdapter().notifyMoreFinish(true);
        Toast.makeText(this, "好像出了点小问题", Toast.LENGTH_SHORT).show();
        hideRefreshProgress();
        full_progressbar.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
        error_view.setVisibility(View.VISIBLE);
        error_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingView();
                presenter.fetchNextPage();
            }
        });
    }

    @Override
    public void showRefreshProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshProgress() {
        swipeRefreshLayout.setRefreshing(false);
        error_view.setVisibility(View.GONE);
        full_progressbar.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showList(List datalist) {
        hideRefreshProgress();
        if (isRefresh) {
            presenter.getAdapter().getDataSource().clear();
            presenter.getAdapter().addDataSource(datalist);
            presenter.getAdapter().notifyRefreshFinish();
            isRefresh = false;
        } else {
            presenter.getAdapter().addDataSource(datalist);
            if (datalist.size() > 0)
                presenter.getAdapter().notifyMoreFinish(true);
            else
                presenter.getAdapter().notifyMoreFinish(false);
        }

//        if (App.getDbHash() != null)
//            App.getDbHash().put("firstlist",datalist);
//        else
//            Toast.makeText(this,"DbHash is null!!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showList(List datalist, boolean hasnext) {
        this.showList(datalist);
        presenter.getAdapter().notifyMoreFinish(hasnext);
    }

    public void showCacheList(List datalist) {
        hideRefreshProgress();
        presenter.getAdapter().getDataSource().clear();
        presenter.getAdapter().addDataSource(datalist);
        presenter.getAdapter().notifyMoreFinish(false);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
//
//        if (App.getDbHash() != null) {
//            List datalist = App.getDbHash().get("firstlist");
//            if (datalist != null && datalist.size() > 0)
//                showCacheList(datalist);
//        }
//        recyclerView.addItemDecoration(new LMRecyclerView.DividerItemDecoration(this,LMRecyclerView.DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        presenter.fetchNextPage();
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        presenter.currentHttpModel = null;
        presenter.refreshBefore();
        presenter.fetchNextPage();
    }
}
