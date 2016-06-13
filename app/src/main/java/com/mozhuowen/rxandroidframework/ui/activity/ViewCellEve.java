package com.mozhuowen.rxandroidframework.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mozhuowen.rxandroid.adapter.BaseHolder;
import com.mozhuowen.rxandroidframework.R;
import com.mozhuowen.rxandroidframework.model.entity.MovieItem;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Awen on 16/6/7.
 * Email:mozhuowen@gmail.com
 */
public class ViewCellEve extends BaseHolder<MovieItem> {

    Context context;
    String dataId;

    @Bind(R.id.text)
    TextView textView;
    @Bind(R.id.image)
    ImageView image;

    public ViewCellEve(Context context, int layoutResId, ViewGroup parent, int viewType) {
        super(context, layoutResId, parent, viewType);
        this.context = context;
    }

    @Override
    public void bindData(MovieItem data) {
        dataId = data._id;
        textView.setText(data.title);
        Glide.with(mContext)
                .load(data.image_urls.get(0))
                .crossFade()
                .into(image);
    }

    @OnClick(R.id.item_layout)
    @Override
    public void onClickItem() {
        Intent intent = new Intent(context,EditEveActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id",dataId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public boolean onLongClickItem() {
        return false;
    }
}
