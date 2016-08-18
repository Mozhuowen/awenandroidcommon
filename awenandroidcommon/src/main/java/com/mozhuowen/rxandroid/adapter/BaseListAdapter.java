package com.mozhuowen.rxandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.mozhuowen.rxandroid.CommonListener;
import com.mozhuowen.rxandroid.ui.LMRecyclerView;
import com.mozhuowen.util.LogUtil;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Awen on 16/5/12.
 * Email:mozhuowen@gmail.com
 */
public class BaseListAdapter<T extends BaseHolder,R> extends RecyclerView.Adapter<BaseHolder> {
    //ITEM类型
    protected static final int TYPE_HEADER = 2;
    protected static final int TYPE_FOOTER = 3;
    protected static final int TYPE_LIST = 4;
    protected ListLoadType loadType = ListLoadType.AUTO_LOAD;
    protected boolean enableHeader = false;
    protected boolean enableFooter = false;
    protected boolean isLoadingMore = false;
    protected LMRecyclerView.LoadMoreListener loadMoreListener;
    protected int mLoastLoadMorePosition;

    protected int mLayoutResId;
    protected List<R> mDataSource = new ArrayList<>();
    protected Context mContext;
    protected Class viewHolderClass;   //ViewHolder类
    protected BaseHolder headerView;  //header
    protected RecyclerView recyclerView;

    public BaseListAdapter(Context context,int layoutResId, List<R> dataSource,Class viewHolderClass) {
        this.mLayoutResId = layoutResId;
        this.mDataSource = dataSource;
        this.mContext = context;
        this.viewHolderClass = viewHolderClass;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {

            if (this.recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                RecyclerView.ViewHolder viewHolder = new FooterViewHolder(mContext, parent);
                StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams();
                layoutParams.setFullSpan(true);
                viewHolder.itemView.setLayoutParams(layoutParams);
                return (BaseHolder) viewHolder;
            }

            return new FooterViewHolder(mContext, parent);
        } else if (viewType == TYPE_HEADER)
            return headerView;
        else
            return (BaseHolder) newTclass(viewHolderClass,mLayoutResId,parent,viewType);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == TYPE_LIST) {
            R dataItem = mDataSource.get(position);
            holder.bindData(dataItem);
        }
    }

    @Override
    public int getItemCount() {
        int count = mDataSource.size();
        if (enableHeader && count > 0) count++;
        if (enableFooter && count > 0) count++;
        return count;
    }

    @Override
    public int getItemViewType(int position){
        int footerPosition=getItemCount() - 1;
        if (footerPosition == position && enableFooter) {
            return TYPE_FOOTER;
        } else {
            return TYPE_LIST;
        }
    }

    private Object newTclass(Class<T> clazz,int layoutResId,ViewGroup parentView,int viewType){
        Logger.d(""+clazz.getSimpleName()+" "+clazz.getName());
        try {
            Constructor<?> cons[] = clazz.getConstructors();
            return cons[0].newInstance(mContext,layoutResId,parentView,viewType);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        LogUtil.d("returning null!");
        return null;
    }

    protected Object newTclass(Class<T> clazz, int layoutResId, ViewGroup parentView, int viewType, CommonListener listener){
        try {
            Constructor<?> cons[] = clazz.getConstructors();
            return cons[0].newInstance(mContext,layoutResId,parentView,viewType,listener);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Logger.e("returning null!");
        return null;
    }

    /**
     * 通知更多的数据已经加载
     * <p>
     * 每次加载完成之后添加了Data数据，用notifyItemRemoved来刷新列表展示，
     * 而不是用notifyDataSetChanged来刷新列表
     *
     * @param hasMore
     */
    public void notifyMoreFinish(boolean hasMore){
        setAutoLoadMoreEnable(hasMore);
        if (loadType == ListLoadType.AUTO_LOAD)
            notifyItemRemoved(mLoastLoadMorePosition);
        else {
            notifyItemChanged(mLoastLoadMorePosition + 1);
            //getAdapter().notifyDataSetChanged();
        }
        isLoadingMore = false;
    }

    public void notifyRefreshFinish() {
        setAutoLoadMoreEnable(true);
        isLoadingMore = false;
        notifyDataSetChanged();
    }

    public Class getViewHolderClass() {
        return viewHolderClass;
    }

    public void setViewHolderClass(Class viewHolderClass) {
        this.viewHolderClass = viewHolderClass;
    }

    public void setLoadingMore(boolean loadingMore) {
        isLoadingMore = loadingMore;
    }

    public ListLoadType getLoadType() {
        return loadType;
    }

    public List<R> getDataSource() {
        return mDataSource;
    }

    public void addDataSource(List<R> addDataSource) {
        this.mDataSource.addAll(addDataSource);
    }

    public boolean isEnableFooter() {
        return enableFooter;
    }

    public boolean isLoadingMore() {
        return isLoadingMore;
    }

    public boolean isEnableHeader() {
        return enableHeader;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public RecyclerView getRecyclerView() {
        return this.recyclerView;
    }

    /**
     * 设置是否支持自动加载更多
     *
     * @param autoLoadMore
     */
    public void setAutoLoadMoreEnable(boolean autoLoadMore){
        enableFooter=autoLoadMore;
    }

    public void setLoastLoadMorePosition(int mLoastLoadMorePosition) {
        this.mLoastLoadMorePosition = mLoastLoadMorePosition;
    }
}
