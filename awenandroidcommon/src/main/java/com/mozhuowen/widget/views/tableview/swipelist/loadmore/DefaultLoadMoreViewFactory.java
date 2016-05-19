/*
 * Create by Awen 16-1-11 下午6:02
 */
package com.mozhuowen.widget.views.tableview.swipelist.loadmore;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mozhuowen.R;


public class DefaultLoadMoreViewFactory implements ILoadViewMoreFactory {

    @Override
    public ILoadMoreView madeLoadMoreView() {
        return new LoadMoreHelper();
    }

    private class LoadMoreHelper implements ILoadMoreView {

        protected View footView;
        protected TextView footerTv;
        protected ProgressBar footerBar;

        protected OnClickListener onClickRefreshListener;

        @Override
        public void init(FootViewAdder footViewHolder, OnClickListener onClickRefreshListener) {
            footView = footViewHolder.addFootView(R.layout.loadmore_default_footer);
            footerTv = (TextView) footView.findViewById(R.id.loadmore_default_footer_tv);
            footerBar = (ProgressBar) footView.findViewById(R.id.loadmore_default_footer_progressbar);
            this.onClickRefreshListener = onClickRefreshListener;
            showNormal();
        }

        @Override
        public void showNormal() {
            footerTv.setText("点击加载更多");
            footerBar.setVisibility(View.GONE);
            footerTv.setOnClickListener(onClickRefreshListener);
        }

        @Override
        public void showLoading() {
            footerTv.setText("正在加载中...");
            footerBar.setVisibility(View.VISIBLE);
            footerTv.setOnClickListener(null);
        }

        @Override
        public void showFail(Exception exception) {
            footerTv.setText("加载失败，点击重新");
            footerBar.setVisibility(View.GONE);
            footerTv.setOnClickListener(onClickRefreshListener);
        }

        @Override
        public void hideLoadMore() {
            if (footView != null)
                footView.setVisibility(View.GONE);
        }

        @Override
        public void setVisibility() {
            if (footView != null)
                footView.setVisibility(View.VISIBLE);
        }

        @Override
        public void showNomore() {
            footerTv.setText("暂无更多");
            footerBar.setVisibility(View.GONE);
            footerTv.setOnClickListener(null);
        }

    }

}
