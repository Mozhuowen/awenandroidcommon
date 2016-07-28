package com.mozhuowen.rxandroidframework.ui.iView;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mozhuowen.rxandroid.adapter.BaseHolder;
import com.mozhuowen.rxandroidframework.R;

import butterknife.Bind;

/**
 * Created by Awen on 16/7/28.
 * Email:mozhuowen@gmail.com
 */
public class SectionListHeaderCell extends BaseHolder<String> {

    @Bind(R.id.text)
    TextView text;

    public SectionListHeaderCell(Context context, int layoutResId, ViewGroup parent, int viewType) {
        super(context, layoutResId, parent, viewType);
    }

    @Override
    public void bindData(String data) {
        text.setText(data);
        mView.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_green_light));
    }

    @Override
    public void onClickItem() {

    }

    @Override
    public boolean onLongClickItem() {
        return false;
    }
}
