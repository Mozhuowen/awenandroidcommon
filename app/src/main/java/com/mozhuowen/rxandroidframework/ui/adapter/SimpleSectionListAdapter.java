package com.mozhuowen.rxandroidframework.ui.adapter;

import android.content.Context;

import com.mozhuowen.rxandroid.adapter.BaseHolder;
import com.mozhuowen.rxandroid.adapter.SectionListAdapter;
import com.mozhuowen.rxandroidframework.ui.iView.SectionListHeaderCell;
import com.mozhuowen.rxandroidframework.ui.iView.SectionListItemCell;

import java.util.List;

/**
 * Created by Awen on 16/7/28.
 * Email:mozhuowen@gmail.com
 */
public class SimpleSectionListAdapter extends SectionListAdapter<String,SectionListItemCell,SectionListHeaderCell> {
    public SimpleSectionListAdapter(Context context, int itemLayoutResId, int headerLayoutResId, List<String> itemdatesource, Class itemClass, Class headerClass) {
        super(context, itemLayoutResId, headerLayoutResId, itemdatesource, itemClass, headerClass);
    }

    @Override
    public long getHeaderId(int position) {
//        if (position == 0) {
//            return -1;
//        } else {
//            return mDataSource.get(position).charAt(0);
//        }
        return mDataSource.get(position).charAt(0);
    }

    @Override
    public void onBindHeaderViewHolder(BaseHolder holder, int position) {
        holder.bindData(String.valueOf(mDataSource.get(position).charAt(0)));
    }
}
