package com.mozhuowen.rxandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.mozhuowen.rxandroid.ui.LMRecyclerView;
import com.mozhuowen.util.LogUtil;

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
    private static final int TYPE_HEADER = 2;
    private static final int TYPE_FOOTER = 3;
    private static final int TYPE_LIST = 4;
    private ListLoadType loadType = ListLoadType.AUTO_LOAD;
    private boolean enableHeader = false;
    private boolean enableFooter = false;
    private boolean isLoadingMore = false;
    private LMRecyclerView.LoadMoreListener loadMoreListener;
    private int mLoastLoadMorePosition;

    private int mLayoutResId;
    private List<R> mDataSource = new ArrayList<>();
    private Context mContext;
    private Class viewHolderClass;   //ViewHolder类
    private BaseHolder headerView;  //header

    public BaseListAdapter(Context context,int layoutResId, List<R> dataSource,Class viewHolderClass) {
        this.mLayoutResId = layoutResId;
        this.mDataSource = dataSource;
        this.mContext = context;
        this.viewHolderClass = viewHolderClass;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER)
            return new FooterViewHolder(mContext,parent);
        else if (viewType == TYPE_HEADER)
            return headerView;
        else
            return (T)newTclass(viewHolderClass,mLayoutResId,parent,viewType);
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

    private T newTclass(Class<T> clazz,int layoutResId,ViewGroup parentView,int viewType){
        try {
            Constructor<?> cons[] = clazz.getConstructors();
            return (T)cons[0].newInstance(mContext,layoutResId,parentView,viewType);
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
