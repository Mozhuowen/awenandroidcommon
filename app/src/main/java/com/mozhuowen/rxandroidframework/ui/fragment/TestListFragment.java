package com.mozhuowen.rxandroidframework.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mozhuowen.rxandroid.fragments.BaseListFragment;
import com.mozhuowen.rxandroid.ui.LMRecyclerView;
import com.mozhuowen.rxandroid.ui.handlers.ActionBarHandler;
import com.mozhuowen.rxandroid.ui.handlers.ActionBarHandlerDefault;
import com.mozhuowen.rxandroidframework.R;
import com.mozhuowen.rxandroidframework.presenter.TestPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Awen on 16/5/26.
 * Email:mozhuowen@gmail.com
 */
public class TestListFragment extends BaseListFragment {

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
    protected int getLayoutResId() {
        return R.layout.activity_main_test;
    }

    @Override
    protected void initCreateView(View view) {
        ButterKnife.bind(this,view);
    }

    @Override
    protected void initPresenter() {
        presenter = new TestPresenter(this.getActivity(), this);
        presenter.init();
        presenter.setRecyclerView(recyclerView);
        presenter.setLoadMoreListener(this);

        presenter.fetchData();
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
        Toast.makeText(this.getActivity(), "好像出了点小问题", Toast.LENGTH_SHORT).show();

        full_progressbar.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
        error_view.setVisibility(View.VISIBLE);
        error_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingView();
//                presenter.page = 1;
                presenter.fetchData();
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
            presenter.getAdapter().notifyMoreFinish(true);
            isRefresh = false;
        } else {
            presenter.getAdapter().addDataSource(datalist);
            if (datalist.size() > 0)
                presenter.getAdapter().notifyMoreFinish(true);
            else
                presenter.getAdapter().notifyMoreFinish(false);
        }
    }

    @Override
    public void showList(List datalist, boolean hasnext) {

    }

    @Override
    public void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);

        recyclerView.addItemDecoration(new LMRecyclerView.DividerItemDecoration(this.getActivity(),LMRecyclerView.DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        presenter.fetchData();
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
//        presenter.page = 1;
        presenter.fetchData();
    }

    @Override
    protected boolean enableActionBar() {
        return true;
    }

    @Override
    protected ActionBarHandler getActionBarHandler() {
        return new ActionBarHandlerDefault(getActivity());
    }

    @Override
    protected String getTitle() {
        return getArguments().getString("content");
    }

    @Override
    protected int getMenuResid() {
        return 0;
    }
}
