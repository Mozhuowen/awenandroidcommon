package com.mozhuowen.rxandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mozhuowen.widget.views.multimageselector.utils.ScreenUtils;

import java.util.List;

/**
 * Created by Awen on 16/6/28.
 * Email:mozhuowen@gmail.com
 */
public abstract class SimpleGridAdapter<T> extends BaseAdapter {

    private List<T> list;
    protected Context context;
    private int itemResId;

    protected final int mGridWidth;

    public SimpleGridAdapter(Context context,List<T> list,int itemResId,int column) {
        this.context = context;
        this.list = list;
        this.itemResId = itemResId;
        mGridWidth = ScreenUtils.getScreenSize(context).x;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder<T> viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(itemResId,parent,false);
            viewHolder = getViewHolder();
        } else {
            viewHolder = (ViewHolder<T>) convertView.getTag();
        }

        if (viewHolder != null)
            viewHolder.bindData(getItem(position),context,convertView,position);

        return convertView;
    }

    public abstract ViewHolder<T> getViewHolder();

    public interface ViewHolder<T>
    {
       void bindData(final T data,Context context,View view,int position);
    }
}
