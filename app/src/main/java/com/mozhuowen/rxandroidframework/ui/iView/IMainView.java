package com.mozhuowen.rxandroidframework.ui.iView;


import com.mozhuowen.rxandroidframework.model.entity.Meizi;

import java.util.List;

/**
 * Created by xybcoder on 2016/3/1.
 */
public interface IMainView extends IBaseView{
    void showProgress();
    void hideProgress();
    void showErrorView();
    void showNoMoreData();
    void showMeiziList(List<Meizi> meiziList);
}
