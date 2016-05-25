package com.mozhuowen.rxandroidframework.ui.activity;

import android.os.Bundle;

import com.mozhuowen.rxandroid.activity.BottomNavigationActivity;
import com.mozhuowen.rxandroid.ui.handlers.ActionBarHandler;
import com.mozhuowen.rxandroid.ui.handlers.ActionBarHandlerDefault;
import com.mozhuowen.widget.material.fragment.BlankFragment;
import com.mozhuowen.widget.views.bottomnavigation.Controller;
import com.mozhuowen.widget.views.bottomnavigation.TabItemBuilder;
import com.mozhuowen.widget.views.bottomnavigation.TabLayoutMode;
import com.mozhuowen.widget.views.bottomnavigation.TabStripBuild;

/**
 * Created by Awen on 16/5/20.
 * Email:mozhuowen@gmail.com
 */
public class Test3Activity extends BottomNavigationActivity {

    @Override
    public String getTitleString() {
        return "Test3Activity";
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public boolean isActionbarVisible() {
        return true;
    }

    @Override
    public boolean isDisplayHomeAsUpEnabled() {
        return false;
    }

    @Override
    protected boolean enableActionBarShadow() {
        return false;
    }

    @Override
    protected ActionBarHandler getActionBarHandler() {
        return new ActionBarHandlerDefault(this);
    }

    @Override
    protected boolean enableBackArrow() {
        return false;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public Controller getBottomnavigationController(TabStripBuild builder) {
        int[] testColors = {0xFF00796B,0xFF00796B,0xFF00796B,0xFF00796B,0xFF00796B};

        //用TabItemBuilder构建一个导航按钮
        TabItemBuilder tabItemBuilder = new TabItemBuilder(this).create()
                .setDefaultIcon(android.R.drawable.ic_menu_send)
                .setText("信息")
                .setSelectedColor(testColors[0])
                .setTag("A")
                .build();

        Controller controller = builder
                .addTabItem(tabItemBuilder)
                .addTabItem(android.R.drawable.ic_menu_compass, "位置",testColors[1])
                .addTabItem(android.R.drawable.ic_menu_search, "搜索",testColors[2])
                .addTabItem(android.R.drawable.ic_menu_help, "帮助",testColors[3])
//                .setMode(TabLayoutMode.HIDE_TEXT)
//                .setMode(TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                .setMode(TabLayoutMode.HIDE_TEXT| TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                .build();

        return controller;
    }

    @Override
    public FragmentHandle getFragmentHandle() {

        FragmentHandle handle = new FragmentHandle();
        for (int i=0;i<4;i++) {
            BlankFragment fragment = new BlankFragment();
            Bundle bundle = new Bundle();
            bundle.putString("content",i+"");
            fragment.setArguments(bundle);
            handle.addFragment(fragment);
        }

        return handle;
    }

}
