package com.mozhuowen.rxandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mozhuowen.R;

/**
 * Created by Awen on 16/5/14.
 * Email:mozhuowen@gmail.com
 */
public class FooterViewHolder extends BaseHolder {

    public FooterViewHolder(Context context,ViewGroup parent) {
        super(LayoutInflater.from(context).inflate(R.layout.recyclerview_footer_loading,parent,false));
    }

    @Override
    public void bindData(Object data) {

    }

    @Override
    public void onClickItem() {

    }

    @Override
    public boolean onLongClickItem() {
        return false;
    }
}
