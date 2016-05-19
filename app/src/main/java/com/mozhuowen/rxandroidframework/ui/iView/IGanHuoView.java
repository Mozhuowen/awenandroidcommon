package com.mozhuowen.rxandroidframework.ui.iView;


import com.mozhuowen.rxandroidframework.model.entity.Gank;

import java.util.List;

/**
 * Created by xybcoder on 2016/3/1.
 */
public interface IGanHuoView extends IBaseView {

    void showProgressBar();
    void hideProgressBar();
    void showErrorView();
    void showNoMoreData();
    void showListView(List<Gank> gankList);
}
