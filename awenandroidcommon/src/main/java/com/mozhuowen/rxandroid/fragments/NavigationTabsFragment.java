package com.mozhuowen.rxandroid.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.mozhuowen.R;
import com.mozhuowen.rxandroid.adapter.ViewPagerAdapter;
import com.mozhuowen.rxandroid.model.ViewPagerItem;
import com.mozhuowen.rxandroid.ui.Toolbar;
import com.mozhuowen.rxandroid.ui.handlers.ActionBarHandler;
import com.mozhuowen.rxandroid.ui.handlers.TabsActionBarHandler;
import com.mozhuowen.rxandroid.ui.handlers.ViewPagerHandler;

import java.util.List;

/**
 * Created by Awen on 16/8/11.
 * Email:mozhuowen@gmail.com
 */
public abstract class NavigationTabsFragment extends BaseFragment implements com.mozhuowen.rxandroid.ui.ViewPager {

    private ActionBarHandler mActionBarHandler;
    private Toolbar mCustomToolbar;

    protected ViewPager mViewPager;
    protected PagerSlidingTabStrip mViewPagerTabs;
    private List<ViewPagerItem> mViewPagerItems;
    private ViewPager.OnPageChangeListener mUserOnPageChangeListener;
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) super.onCreateView(inflater, container, savedInstanceState);
        mActionBarHandler = getActionBarHandler();
        mCustomToolbar = mActionBarHandler.build();
        if (enableLayoutFullScreen()) {
            FrameLayout.LayoutParams params =
                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT /***+ getStatusBarHeight()*/);
            params.setMargins(0,getStatusBarHeight(),0,0);
            view.addView(mCustomToolbar, params);
        } else
            view.addView(mCustomToolbar, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mCustomToolbar.getToolbar().setTitle(getTitle());
        mCustomToolbar.getToolbar().setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        ViewPagerHandler handler = getViewPagerHandler();
        if (handler != null && handler.getViewPagerItems() != null) {
            mViewPagerItems = handler.getViewPagerItems();
        }
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), mViewPagerItems);
//        mViewPagerAdapter = new BNAdapter(getActivity().getSupportFragmentManager(),getActivity());
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);

        int defaultViewPagerPageSelectedPosition = defaultViewPagerPageSelectedPosition();
        if (defaultViewPagerPageSelectedPosition >= 0 &&
                defaultViewPagerPageSelectedPosition < mViewPagerItems.size()) {
            selectPage(defaultViewPagerPageSelectedPosition);
        }

        if (!mViewPagerItems.isEmpty()) showTabs(mViewPager);

        if (getMenuResid() > 0)
            mCustomToolbar.getToolbar().inflateMenu(getMenuResid());
        return view;
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
        mViewPagerTabs.setIndicatorHeight(5);
        mViewPagerTabs.setIndicatorColor(getIndicatorColor());
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
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener userOnPageChangeListener) {
        mUserOnPageChangeListener = userOnPageChangeListener;
    }

    @Override
    public void updateNavigationDrawerTopHandler(ViewPagerHandler viewPagerHandler,
                                                 int defaultViewPagerPageSelectedPosition) {

    }

    @Override
    public void selectPage(int position) {
//        Logger.d("select page "+position);
        mViewPager.setCurrentItem(position);
    }

    protected ActionBarHandler getActionBarHandler(){
        TabsActionBarHandler tabsActionBarHandler = new TabsActionBarHandler(getActivity());
        mViewPagerTabs = tabsActionBarHandler.getPagerSlidingTabStrip();
        return tabsActionBarHandler;
    }

    protected int getLayoutResId() {
        return R.layout.awen_navigationtabs_withmargintop2;
    }

    protected abstract int getMenuResid();
    protected abstract String getTitle();
    protected abstract boolean expandTabs();
    protected abstract int getTextColor();
    protected abstract int getTabsTextPiexlSize();
    protected abstract int getTextSelectedColor();
    protected abstract int getIndicatorColor();
}
