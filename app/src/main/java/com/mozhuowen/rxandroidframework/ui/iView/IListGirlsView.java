package com.mozhuowen.rxandroidframework.ui.iView;


import com.mozhuowen.rxandroidframework.model.entity.Meizi;

import java.util.List;

/**
 * Created by dell on 2016/4/16.
 */
public interface IListGirlsView extends IBaseView{

    void showProgressBar();
    void hideProgressBar();
    void showErrorView();
    void showNoMoreData();
    void showListView(List<Meizi> gankList);
}
