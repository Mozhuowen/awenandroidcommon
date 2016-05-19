package com.mozhuowen.widget.views.tableview;

public interface TableViewDelegate {
    void onClickRow(int section, int row);

    void onLongClickRow(int section,int row);

    void onRefresh();

    void onLoadMore();
}