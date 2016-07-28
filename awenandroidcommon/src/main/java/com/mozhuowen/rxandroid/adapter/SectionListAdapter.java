package com.mozhuowen.rxandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.mozhuowen.widget.views.sectionrecyclerview.StickyRecyclerHeadersAdapter;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Awen on 16/7/27.
 * Email:mozhuowen@gmail.com
 *
 * T->listitem datatype
 * R->headeritem datatype
 * E->listitem viewholder
 * W->headeritem viewholder
 */
public abstract class SectionListAdapter<T,R,E extends BaseHolder,W extends BaseHolder>
        extends RecyclerView.Adapter<BaseHolder>
        implements StickyRecyclerHeadersAdapter<BaseHolder> {

    private List<T> mDataSource = new ArrayList<>();
    private List<R> mHeaderDataSource = new ArrayList<>();
    private Context mContext;
    private Class itemViewHolderClass;
    private Class headerViewHolderClass;
    private RecyclerView recyclerView;
    private int itemLayoutResId;
    private int headerLayoutResId;

    public SectionListAdapter(Context context,int itemLayoutResId,int headerLayoutResId,
                              List<T> itemdatesource,
                              List<R> headerdatasource,
                              Class itemClass,
                              Class headerClass)
    {
        this.mContext = context;
        this.mDataSource = itemdatesource;
        this.mHeaderDataSource = headerdatasource;
        this.itemViewHolderClass = itemClass;
        this.headerViewHolderClass = headerClass;
        this.itemLayoutResId = itemLayoutResId;
        this.headerLayoutResId = headerLayoutResId;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return (E)newTclass(itemViewHolderClass,itemLayoutResId,parent,viewType);
    }

    @Override
    public BaseHolder onCreateHeaderViewHolder(ViewGroup parent) {

        return (W)newTclass(headerViewHolderClass,itemLayoutResId,parent,0);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        T data = mDataSource.get(position);
        holder.bindData(data);
    }

    @Override
    public void onBindHeaderViewHolder(BaseHolder holder, int position) {
        R data = mHeaderDataSource.get(position);
        holder.bindData(data);
    }

    @Override
    public long getItemId(int position) {
        return mDataSource.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
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
        Logger.e("returning null!");
        return null;
    }
}
