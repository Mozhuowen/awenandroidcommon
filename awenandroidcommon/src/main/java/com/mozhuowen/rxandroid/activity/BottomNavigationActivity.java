package com.mozhuowen.rxandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.mozhuowen.R;
import com.mozhuowen.rxandroid.adapter.BNAdapter;
import com.mozhuowen.widget.views.bottomnavigation.Controller;
import com.mozhuowen.widget.views.bottomnavigation.PagerBottomTabLayout;
import com.mozhuowen.widget.views.bottomnavigation.TabStripBuild;
import com.mozhuowen.widget.views.bottomnavigation.listener.OnTabItemSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Awen on 16/5/19.
 * Email:mozhuowen@gmail.com
 */
public abstract class BottomNavigationActivity extends NormalActivity {

    protected ViewPager viewPager;
    protected PagerBottomTabLayout pagerBottomTabLayout;
    protected OnTabItemSelectListener listener;
    protected Controller controller;
    protected BNAdapter bnAdapter;
    private int currSelectedPos = 0;

    @Override
    public void onCreate(Bundle savedStanceState) {
        super.onCreate(savedStanceState);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pagerBottomTabLayout = (PagerBottomTabLayout) findViewById(R.id.bottomnavigation);

        initBottomNavigation();

        if (savedStanceState != null && savedStanceState.getInt("position") > -1 ) {
            controller.setSelect(savedStanceState.getInt("position"));
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.bottom_navigation_layout;
    }

    public void initBottomNavigation() {

        controller = getBottomnavigationController(pagerBottomTabLayout.builder());

        bnAdapter = new BNAdapter(getSupportFragmentManager(),this);
        bnAdapter.setFragments(getFragmentHandle().getFragmentList());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(bnAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currSelectedPos = position;
                controller.setSelect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        listener = new OnTabItemSelectListener(){

            @Override
            public void onSelected(int index, Object tag) {
                currSelectedPos = index;
                viewPager.setCurrentItem(index,false);
            }

            @Override
            public void onRepeatClick(int index, Object tag) {

            }
        };
        controller.addTabItemClickListener(listener);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("position",currSelectedPos);

        super.onSaveInstanceState(outState);
    }

    public abstract Controller getBottomnavigationController(TabStripBuild builder);
    public abstract FragmentHandle getFragmentHandle();

    public static class FragmentHandle
    {
        List<Fragment> fragmentList;
        public FragmentHandle addFragment(Fragment fragment) {
            if ( fragmentList == null ) {
                fragmentList = new ArrayList<>();
            }
            fragmentList.add(fragment);
            return this;
        }
        public List<Fragment> getFragmentList() {
            return this.fragmentList;
        }
    }

}
