package com.mozhuowen.rxandroidframework.ui.iView;

import com.mozhuowen.rxandroid.ui.ListView;
import com.mozhuowen.rxandroidframework.model.entity.MovieItem;

/**
 * Created by Awen on 16/6/8.
 * Email:mozhuowen@gmail.com
 */
public interface TestEveView extends ListView<MovieItem> {

    void onGetData(MovieItem data);
}
