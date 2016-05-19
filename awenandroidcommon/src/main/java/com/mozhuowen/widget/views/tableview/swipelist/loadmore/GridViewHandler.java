/*
 * Create by Awen 16-1-11 下午6:02
 */
package com.mozhuowen.widget.views.tableview.swipelist.loadmore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListAdapter;


public class GridViewHandler implements ViewHandler {

    @Override
    public boolean handleSetAdapter(View contentView, ILoadViewMoreFactory.ILoadMoreView loadMoreView, OnClickListener onClickLoadMoreListener) {
        final GridViewWithHeaderAndFooter gridView = (GridViewWithHeaderAndFooter) contentView;
        ListAdapter adapter = gridView.getAdapter();
        boolean hasInit = false;
        if (loadMoreView != null) {
            final Context context = gridView.getContext().getApplicationContext();
            loadMoreView.init(new ILoadViewMoreFactory.FootViewAdder() {

                @Override
                public View addFootView(int layoutId) {
                    View view = LayoutInflater.from(context).inflate(layoutId, gridView, false);
                    return addFootView(view);
                }

                @Override
                public View addFootView(View view) {
                    gridView.addFooterView(view);
                    return view;
                }
            }, onClickLoadMoreListener);
            hasInit = true;
            if (null != adapter) {
                gridView.setAdapter(adapter);
            }
        }
        return hasInit;
    }

    @Override
    public void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener) {
        GridViewWithHeaderAndFooter gridView = (GridViewWithHeaderAndFooter) contentView;
        gridView.setOnScrollListener(new GridViewOnScrollListener(onScrollBottomListener));
        gridView.setOnItemSelectedListener(new GridViewOnItemSelectedListener(onScrollBottomListener));
    }

    /**
     * 针对于电视 选择到了底部项的时候自动加载更多数据
     */
    private class GridViewOnItemSelectedListener implements OnItemSelectedListener {
        private OnScrollBottomListener onScrollBottomListener;

        public GridViewOnItemSelectedListener(OnScrollBottomListener onScrollBottomListener) {
            super();
            this.onScrollBottomListener = onScrollBottomListener;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            if (adapterView.getLastVisiblePosition() + 1 == adapterView.getCount()) {// 如果滚动到最后一行
                if (onScrollBottomListener != null) {
                    onScrollBottomListener.onScorllBootom();
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    ;

    /**
     * 滚动到底部自动加载更多数据
     */
    private static class GridViewOnScrollListener implements OnScrollListener {
        private OnScrollBottomListener onScrollBottomListener;

        public GridViewOnScrollListener(OnScrollBottomListener onScrollBottomListener) {
            super();
            this.onScrollBottomListener = onScrollBottomListener;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && view.getLastVisiblePosition() + 1 == view.getCount()) {// 如果滚动到最后一行
                if (onScrollBottomListener != null) {
                    onScrollBottomListener.onScorllBootom();
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }
}
