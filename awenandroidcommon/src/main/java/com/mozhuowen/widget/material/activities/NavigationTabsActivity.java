package com.mozhuowen.widget.material.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.blunderer.materialdesignlibrary.adapters.ViewPagerAdapter;
import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.blunderer.materialdesignlibrary.handlers.ViewPagerHandler;
import com.blunderer.materialdesignlibrary.models.ViewPagerItem;
import com.mozhuowen.R;
import com.mozhuowen.widget.material.handlers.TabsActionBarHandler;

import java.util.List;

/**
 * Created by Awen on 16/3/24.
 * Email:mozhuowen@gmail.com
 */
public abstract class NavigationTabsActivity extends AAActivity
        implements com.blunderer.materialdesignlibrary.interfaces.ViewPager {

    protected ViewPager mViewPager;
    protected PagerSlidingTabStrip mViewPagerTabs;
    private List<ViewPagerItem> mViewPagerItems;
    private ViewPager.OnPageChangeListener mUserOnPageChangeListener;
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected ActionBarHandler getActionBarHandler(){
        TabsActionBarHandler tabsActionBarHandler = new TabsActionBarHandler(this);
        mViewPagerTabs = tabsActionBarHandler.getPagerSlidingTabStrip();
        return tabsActionBarHandler;
    }

    @Override
    public void selectPage(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener userOnPageChangeListener) {
        mUserOnPageChangeListener = userOnPageChangeListener;
    }

    @Override
    public void updateNavigationDrawerTopHandler(ViewPagerHandler viewPagerHandler,
                                                 int defaultViewPagerPageSelectedPosition) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.awen_navigationtabs_withmargintop);

        ViewPagerHandler handler = getViewPagerHandler();
        if (handler != null && handler.getViewPagerItems() != null) {
            mViewPagerItems = handler.getViewPagerItems();
        }

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mViewPagerItems);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);

        int defaultViewPagerPageSelectedPosition = defaultViewPagerPageSelectedPosition();
        if (defaultViewPagerPageSelectedPosition >= 0 &&
                defaultViewPagerPageSelectedPosition < mViewPagerItems.size()) {
            selectPage(defaultViewPagerPageSelectedPosition);
        }

        if (!mViewPagerItems.isEmpty()) showTabs(mViewPager);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getTitleString());
        actionBar.setDisplayHomeAsUpEnabled(isDisplayHomeAsUpEnabled());
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle(getTitleString());
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initSelfViews();
    }

    private void showTabs(ViewPager pager) {
        setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < ((ViewGroup) mViewPagerTabs.getChildAt(0)).getChildCount(); i++) {
                    ((TextView) ((ViewGroup) mViewPagerTabs.getChildAt(0)).getChildAt(i)).setTextColor(getTextColor());
                }
                ((TextView) ((ViewGroup) mViewPagerTabs.getChildAt(0)).getChildAt(position)).setTextColor(getTextSelectedColor());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPagerTabs.setTabPaddingLeftRight(10);
        mViewPagerTabs.setTextColor(getTextColor());
        mViewPagerTabs.setIndicatorColor(getResources().getColor(android.R.color.black));
        mViewPagerTabs.setDividerColor(android.R.color.transparent);
        mViewPagerTabs.setTextSize(getTabsTextPiexlSize());
        mViewPagerTabs.setShouldExpand(expandTabs());
        mViewPagerTabs.setViewPager(pager);
        mViewPagerTabs.setTabPaddingLeftRight(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mViewPagerTabs.setTabBackground(android.R.attr.selectableItemBackground);
        }

        //设置TAB字体颜色的变化
        mViewPagerTabs.setOnPageChangeListener(mUserOnPageChangeListener);
        ((TextView)((ViewGroup) mViewPagerTabs.getChildAt(0)).getChildAt(0)).setTextColor(getTextSelectedColor());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up
        return false;
    }

    public abstract String getTitleString();
    public abstract boolean isDisplayHomeAsUpEnabled();
    protected abstract void initSelfViews();
    protected abstract boolean expandTabs();
    protected abstract int getTextColor();
    protected abstract int getTabsTextPiexlSize();
    protected abstract int getTextSelectedColor();
}
