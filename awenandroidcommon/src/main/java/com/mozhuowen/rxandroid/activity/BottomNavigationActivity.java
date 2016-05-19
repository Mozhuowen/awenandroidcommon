package com.mozhuowen.rxandroid.activity;

import android.os.Bundle;
import android.view.View;

import com.mozhuowen.R;
import com.mozhuowen.rxandroid.activity.handlers.BottomNavigationHandler;
import com.mozhuowen.rxandroid.adapter.BNAdapter;

/**
 * Created by Awen on 16/5/19.
 * Email:mozhuowen@gmail.com
 */
public abstract class BottomNavigationActivity extends NormalActivity {

    protected BNAdapter bnAdapter;

    @Override
    public void onCreate(Bundle savedStanceState) {
        super.onCreate(savedStanceState);


    }

    @Override
    protected int getLayoutResId() {
        return R.layout.bottom_navigation_layout;
    }

    public void initBottomNavigation() {

    }

    public abstract View[] getBottomViews();
    public abstract BottomNavigationHandler getBottonNavigationHandler();
}
