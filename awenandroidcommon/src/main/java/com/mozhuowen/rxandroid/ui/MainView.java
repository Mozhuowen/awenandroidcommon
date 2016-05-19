package com.mozhuowen.rxandroid.ui;

/**
 * Created by Awen on 16/5/12.
 * Email:mozhuowen@gmail.com
 */
public interface MainView extends BaseView  {

    void showProgress();
    void hideProgress();
    void showErrorView();
    void showNoMoreData();

}
