package com.mozhuowen.widget.views.tableview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.mozhuowen.R;
import com.mozhuowen.widget.views.tableview.swipelist.loadmore.OnLoadMoreListener;
import com.mozhuowen.widget.views.tableview.swipelist.loadmore.SwipeRefreshHelper;

import net.datafans.android.common.widget.table.refresh.RefreshTableViewAdapter;

public class SwipeRefreshTableViewAdapter extends RefreshTableViewAdapter implements
        SwipeRefreshLayout.OnRefreshListener {

    private ListView listView;
    private SwipeRefreshLayout refreshLayout;

    private SwipeRefreshHelper mSwipeRefreshHelper;
    View view;

    @SuppressLint("InflateParams")
    public SwipeRefreshTableViewAdapter(Context context, BaseAdapter adapter) {

        view = LayoutInflater.from(context).inflate(
                R.layout.swipe_refreshlayout, null);
        refreshLayout = (SwipeRefreshLayout) view
                .findViewById(R.id.id_swipe_ly);

        listView = (ListView) view.findViewById(R.id.id_listview);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        mSwipeRefreshHelper = new SwipeRefreshHelper(refreshLayout);
        mSwipeRefreshHelper.setLoadMoreEnable(true);
        mSwipeRefreshHelper.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                SwipeRefreshTableViewAdapter.super.loadMore();
            }
        });
    }

    @Override
    public View getRootView() {
        return view;
    }


    @Override
    public ListView getListView() {
        return listView;
    }

    @Override
    public void enableRefresh(boolean enable) {
        refreshLayout.setEnabled(enable);

        if (enable)
            refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void enableLoadMore(boolean enable) {
        mSwipeRefreshHelper.setLoadMoreEnable(enable);
    }

    @Override
    public void endRefresh() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void endLoadMore() {
        mSwipeRefreshHelper.loadMoreComplete(true);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshHelper.setLoadMoreEnable(true);
        mSwipeRefreshHelper.setLoadMoreViewVisibility();
        refresh();
    }

    @Override
    public void enableAutoLoadMore(boolean enable) {
        // TODO Auto-generated method stub
        mSwipeRefreshHelper.setLoadMoreEnable(true);
    }

    @Override
    public void loadOver(boolean over) {
        // TODO Auto-generated method stub
        if (over) {
            mSwipeRefreshHelper.setLoadMoreEnable(false);
            mSwipeRefreshHelper.hideLoadMoreView();
        }else {
            mSwipeRefreshHelper.setLoadMoreEnable(true);
        }
    }

}