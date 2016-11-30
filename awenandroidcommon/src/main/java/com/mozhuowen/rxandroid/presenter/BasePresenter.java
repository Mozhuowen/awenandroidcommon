package com.mozhuowen.rxandroid.presenter;

import android.content.Context;

import com.mozhuowen.rxandroid.ui.BaseView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by Awen on 16/5/12.
 * Email:mozhuowen@gmail.com
 */
public abstract class BasePresenter<T extends BaseView>  {

    protected List<Subscription> subscriptions = new ArrayList<>();
    protected Context context;
    protected T mView;

    public BasePresenter(Context context, T iView) {
        this.context = context;
        this.mView = iView;
    }

    public void init(){
        mView.initViews();
    }

    public void release() {
        if (subscriptions != null && subscriptions.size() > 0) {
            for (Subscription s:subscriptions) {
                s.unsubscribe();
            }
        }
    }

    //方便释放回收
    public void addSubscription(Subscription subscription)
    {
        subscriptions.add(subscription);
    }
}
