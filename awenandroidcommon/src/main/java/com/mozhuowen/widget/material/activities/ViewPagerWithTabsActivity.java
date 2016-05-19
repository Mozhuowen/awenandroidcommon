package com.mozhuowen.widget.material.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.blunderer.materialdesignlibrary.adapters.ViewPagerAdapter;
import com.blunderer.materialdesignlibrary.handlers.ViewPagerHandler;
import com.blunderer.materialdesignlibrary.models.ViewPagerItem;
import com.blunderer.materialdesignlibrary.views.ToolbarSearch;
import com.mozhuowen.R;

import java.util.ArrayList;
import java.util.List;

/**拓展原组件部分功能*/
public abstract class ViewPagerWithTabsActivity extends AAActivity
        implements com.blunderer.materialdesignlibrary.interfaces.ViewPager {

    protected ViewPager mViewPager;
    protected PagerSlidingTabStrip mViewPagerTabs;
    private List<ViewPagerItem> mViewPagerItems;
    private ViewPager.OnPageChangeListener mUserOnPageChangeListener;
    private ViewPagerAdapter mViewPagerAdapter;

    public ViewPagerWithTabsActivity() {
        mViewPagerItems = new ArrayList<>();
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
        if (viewPagerHandler == null) viewPagerHandler = new ViewPagerHandler(this);
        mViewPagerItems.clear();
        mViewPagerItems.addAll(viewPagerHandler.getViewPagerItems());
        mViewPagerAdapter.notifyDataSetChanged();

        selectPage(defaultViewPagerPageSelectedPosition);

        if (!mViewPagerItems.isEmpty()) showTabs(mViewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getLayoutResId() > 0)
            super.onCreate(savedInstanceState,getLayoutResId());
        else
            super.onCreate(savedInstanceState, R.layout.awen_tabs_withmargintop);

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

        mViewPagerTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        if (!mViewPagerItems.isEmpty()) showTabs(mViewPager);

        ActionBar actionBar = getSupportActionBar();
        if (isActionbarVisible()) {
            actionBar.setTitle(getTitleString());
            actionBar.setDisplayHomeAsUpEnabled(isDisplayHomeAsUpEnabled());
//            final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//            upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
//            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else
            actionBar.hide();

        initSelfViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ToolbarSearch.SEARCH_REQUEST_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
        } else if (mViewPagerItems != null && mViewPagerItems.size() > 0 && mViewPager != null) {
            int tabPosition = mViewPager.getCurrentItem();
            if (tabPosition >= 0 && tabPosition < mViewPagerItems.size()) {
                mViewPagerItems.get(tabPosition).getFragment()
                        .onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private void showTabs(ViewPager pager) {
        setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i=0;i<((ViewGroup) mViewPagerTabs.getChildAt(0)).getChildCount();i++) {
                    ((TextView)((ViewGroup) mViewPagerTabs.getChildAt(0)).getChildAt(i)).setTextColor(getTextColor());
                }
                ((TextView)((ViewGroup) mViewPagerTabs.getChildAt(0)).getChildAt(position)).setTextColor(getTextSelectedColor());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return true;
        }
    }

    protected abstract boolean expandTabs();
    protected abstract int getLayoutResId();
    protected abstract void initSelfViews();
    public abstract boolean isActionbarVisible();
    public abstract boolean isDisplayHomeAsUpEnabled();
    public abstract String getTitleString();
    protected abstract int getTabsTextPiexlSize();
    protected abstract int getTextColor();
    protected abstract int getTextSelectedColor();
}