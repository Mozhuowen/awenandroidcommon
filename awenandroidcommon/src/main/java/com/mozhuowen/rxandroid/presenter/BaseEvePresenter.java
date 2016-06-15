package com.mozhuowen.rxandroid.presenter;

import android.content.Context;

import com.mozhuowen.rxandroid.model.BaseEveHttpModel;
import com.mozhuowen.rxandroid.ui.BaseView;

/**
 * Created by Awen on 16/6/14.
 * Email:mozhuowen@gmail.com
 */
public abstract class BaseEvePresenter<T extends BaseView> {

    protected T mView;
    protected Context context;

    public BaseEvePresenter(Context context,T view) {
        this.context = context;
        this.mView = view;
    }

    public void init() {
        mView.initViews();
    }

    public abstract void release();

    public abstract void addOneItem(BaseEveHttpModel item);

    public abstract void updateOneItem(BaseEveHttpModel item);

    public abstract void deleteOneItem(BaseEveHttpModel item);

    public abstract void getOneItem(String id);

    public abstract void fetchNextPage();

//    public abstract void updateLocalStorage(BaseEveHttpModel item);
//
//    public abstract void updateLocalstorage(List<BaseEveHttpModel> items);
//
//    public abstract BaseEveHttpModel getItemFromLocalStorage(String etag);
}
