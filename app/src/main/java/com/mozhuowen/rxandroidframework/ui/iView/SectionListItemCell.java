package com.mozhuowen.rxandroidframework.ui.iView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mozhuowen.rxandroid.adapter.BaseHolder;
import com.mozhuowen.rxandroidframework.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Awen on 16/7/28.
 * Email:mozhuowen@gmail.com
 */
public class SectionListItemCell extends BaseHolder<String> {

    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.itemlayout)
    View view;

    public SectionListItemCell(Context context, int layoutResId, ViewGroup parent, int viewType) {
        super(context, layoutResId, parent, viewType);
    }

    @Override
    public void bindData(String data) {
        text.setText(data);
    }

    @OnClick(R.id.itemlayout)
    @Override
    public void onClickItem() {
        Toast.makeText(mContext,"item click!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClickItem() {
        return false;
    }
}
