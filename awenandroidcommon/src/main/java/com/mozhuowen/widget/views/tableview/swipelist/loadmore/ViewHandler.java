/*
 * Create by Awen 16-1-11 下午6:02
 */

package com.mozhuowen.widget.views.tableview.swipelist.loadmore;

import android.view.View;
import android.view.View.OnClickListener;


public interface ViewHandler {

	/**
	 * 
	 * @param view
	 * @param adapter
	 * @param loadMoreView
	 * @param onClickListener
	 * @return 是否有 init ILoadMoreView
	 */
	public boolean handleSetAdapter(View contentView, ILoadViewMoreFactory.ILoadMoreView loadMoreView, OnClickListener onClickLoadMoreListener);

	public void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener);

}
