package com.mozhuowen.rxandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Awen on 16/5/12.
 * Email:mozhuowen@gmail.com
 */
public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {

    protected Context mContext;

    public BaseHolder(Context context,int layoutResId, ViewGroup parent, int viewType) {
        super(LayoutInflater.from(context).inflate(layoutResId,parent,false));
        this.mContext = context;
        ButterKnife.bind(this,itemView);
    }

    public BaseHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public abstract void bindData(T data) ;
    public abstract void onClickItem();
    public abstract boolean onLongClickItem();
}
