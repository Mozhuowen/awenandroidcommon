package com.mozhuowen.rxandroid.fragments;

import android.support.v4.widget.SwipeRefreshLayout;

import com.mozhuowen.rxandroid.ui.LMRecyclerView;
import com.mozhuowen.rxandroid.ui.ListView;

/**
 * Created by Awen on 16/5/26.
 * Email:mozhuowen@gmail.com
 */
public abstract class BaseListFragment extends ToolBarFragment implements ListView,
        LMRecyclerView.LoadMoreListener,SwipeRefreshLayout.OnRefreshListener {

}
