package com.mozhuowen.rxandroidframework.ui.activity;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mozhuowen.rxandroid.adapter.BaseHolder;
import com.mozhuowen.rxandroidframework.R;
import com.mozhuowen.rxandroidframework.model.entity.Meizi;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by Awen on 16/5/12.
 * Email:mozhuowen@gmail.com
 */
public class ViewCell extends BaseHolder<Meizi> {

    @Bind(R.id.text)
    TextView textView;
    @Bind(R.id.image)
    ImageView image;

    public ViewCell(Context context, int layoutResId, ViewGroup parent, int viewType) {
        super(context, layoutResId, parent, viewType);

    }

    @Override
    public void bindData(Meizi data) {
        textView.setText(data.url);
        Glide.with(mContext)
                .load(data.url)
                .crossFade()
                .into(image);
    }

    @OnClick(R.id.item_layout)
    @Override
    public void onClickItem() {
        Toast.makeText(mContext,"onClick!",Toast.LENGTH_SHORT).show();
    }

    @OnLongClick(R.id.item_layout)
    @Override
    public boolean onLongClickItem() {
        Toast.makeText(mContext,"onLongClick!",Toast.LENGTH_SHORT).show();
        return true;
    }
}
