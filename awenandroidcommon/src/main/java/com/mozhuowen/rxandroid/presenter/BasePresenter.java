package com.mozhuowen.rxandroid.presenter;

import android.content.Context;

import com.mozhuowen.rxandroid.ui.BaseView;

import rx.Subscription;

/**
 * Created by Awen on 16/5/12.
 * Email:mozhuowen@gmail.com
 */
public abstract class BasePresenter<T extends BaseView>  {

    protected Subscription subscription;
    protected Context context;
    protected T mView;

    public BasePresenter(Context context, T iView) {
        this.context = context;
        this.mView = iView;
    }

    public void init(){
        mView.initViews();
    }

    public abstract void release();

}
