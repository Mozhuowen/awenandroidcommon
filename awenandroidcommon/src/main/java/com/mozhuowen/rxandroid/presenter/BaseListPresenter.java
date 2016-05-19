package com.mozhuowen.rxandroid.presenter;

import android.content.Context;

import com.mozhuowen.rxandroid.ui.ListView;

/**
 * Created by Awen on 16/5/12.
 * Email:mozhuowen@gmail.com
 */
public abstract class BaseListPresenter extends BasePresenter<ListView> {

    public BaseListPresenter(Context context, ListView iView) {
        super(context, iView);
    }

    public abstract void fetchData();
}
