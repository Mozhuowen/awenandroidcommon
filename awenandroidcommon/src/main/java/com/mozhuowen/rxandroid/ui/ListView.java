package com.mozhuowen.rxandroid.ui;

import java.util.List;

/**
 * Created by Awen on 16/5/12.
 * Email:mozhuowen@gmail.com
 */
public interface ListView<T> extends BaseView {

    void showLoadingView();
    void showErrorView();
    void showRefreshProgress();
    void hideRefreshProgress();
    void showList(List<T> datalist);
}
