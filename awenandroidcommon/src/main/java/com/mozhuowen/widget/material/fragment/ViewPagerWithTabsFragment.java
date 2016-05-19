package com.mozhuowen.widget.material.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blunderer.materialdesignlibrary.adapters.ViewPagerAdapter;
import com.blunderer.materialdesignlibrary.fragments.AFragment;
import com.blunderer.materialdesignlibrary.handlers.ViewPagerHandler;
import com.blunderer.materialdesignlibrary.models.ViewPagerItem;
import com.mozhuowen.R;
import com.mozhuowen.widget.views.PagerSlidingTabStrip;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**扩展原组件部分功能*/
public abstract class ViewPagerWithTabsFragment extends AFragment
        implements com.blunderer.materialdesignlibrary.interfaces.ViewPager {

    protected ViewPager mViewPager;
    protected PagerSlidingTabStrip mViewPagerTabs;
    private List<ViewPagerItem> mViewPagerItems;
    private ViewPager.OnPageChangeListener mUserOnPageChangeListener;
    private ViewPagerAdapter mViewPagerAdapter;

    public ViewPagerWithTabsFragment() {
        mViewPagerItems = new ArrayList<>();
    }

    //友盟SESSION统计
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewPagerHandler viewPagerHandler = getViewPagerHandler();
        if (viewPagerHandler == null) viewPagerHandler = new ViewPagerHandler(getActivity());
        mViewPagerItems = viewPagerHandler.getViewPagerItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        if (getLayoutResId() > 0)
            view = inflater.inflate(getLayoutResId(), container, false);
        else
            view = inflater.inflate(R.layout.awen_tabs_home, container, false);

        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(2);
        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), mViewPagerItems);
        mViewPager.setAdapter(mViewPagerAdapter);

        int defaultViewPagerPageSelectedPosition = defaultViewPagerPageSelectedPosition();
        if (defaultViewPagerPageSelectedPosition >= 0 &&
                defaultViewPagerPageSelectedPosition < mViewPagerItems.size()) {
            selectPage(defaultViewPagerPageSelectedPosition);
        }

        mViewPagerTabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);

        if (!mViewPagerItems.isEmpty()) showTabs(mViewPager);


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mViewPagerItems != null && mViewPagerItems.size() > 0 && mViewPager != null) {
            int tabPosition = mViewPager.getCurrentItem();
            if (tabPosition >= 0 && tabPosition < mViewPagerItems.size()) {
                mViewPagerItems.get(tabPosition).getFragment()
                        .onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void selectPage(int position) {
        if (mViewPager != null)
            mViewPager.setCurrentItem(position);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener userOnPageChangeListener) {
        mUserOnPageChangeListener = userOnPageChangeListener;
    }

    @Override
    public void updateNavigationDrawerTopHandler(ViewPagerHandler viewPagerHandler,
                                                 int defaultViewPagerPageSelectedPosition) {
        if (viewPagerHandler == null) viewPagerHandler = new ViewPagerHandler(getActivity());
        mViewPagerItems.clear();
        mViewPagerItems.addAll(viewPagerHandler.getViewPagerItems());
        mViewPagerAdapter.notifyDataSetChanged();

        selectPage(defaultViewPagerPageSelectedPosition);

        if (!mViewPagerItems.isEmpty()) showTabs(mViewPager);
    }

    private void showTabs(ViewPager pager) {
        setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //先全部设成默认颜色
                for (int i = 0; i < ((ViewGroup) mViewPagerTabs.getChildAt(0)).getChildCount(); i++) {
                    if (!mViewPagerTabs.isDoubleLayerInfo[i])
                        ((TextView) ((ViewGroup) mViewPagerTabs.getChildAt(0)).getChildAt(i)).setTextColor(getTextColor());
                    else {
                        ((TextView) ((ViewGroup) ((ViewGroup) mViewPagerTabs.getChildAt(0)).getChildAt(i)).getChildAt(0)).setTextColor(getTextColor());
                        ((TextView) ((ViewGroup) ((ViewGroup) mViewPagerTabs.getChildAt(0)).getChildAt(i)).getChildAt(1)).setTextColor(getTextColor());
                    }
                }
                //再设成选择的颜色
                if (!mViewPagerTabs.isDoubleLayerInfo[position])
                    ((TextView) ((ViewGroup) mViewPagerTabs.getChildAt(0)).getChildAt(position)).setTextColor(getTextSelectedColor());
                else {
                    ((TextView) ((ViewGroup) ((ViewGroup) mViewPagerTabs.getChildAt(0)).getChildAt(position)).getChildAt(0)).setTextColor(getTextSelectedColor());
                    ((TextView) ((ViewGroup) ((ViewGroup) mViewPagerTabs.getChildAt(0)).getChildAt(position)).getChildAt(1)).setTextColor(getTextSelectedColor());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPagerTabs.setAllCaps(allCaps());
        mViewPagerTabs.setTextColor(getTextColor());
        mViewPagerTabs.setDividerColor(android.R.color.transparent);
        mViewPagerTabs.setIndicatorColor(getIndicatorColor());
        mViewPagerTabs.setBackgroundColor(getTabsBackgroundColor());
        mViewPagerTabs.setTextSize(getTabsTextPiexlSize());
        mViewPagerTabs.setShouldExpand(expandTabs());
        mViewPagerTabs.setTabPaddingLeftRight(5);
        mViewPagerTabs.setViewPager(pager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mViewPagerTabs.setTabBackground(android.R.attr.selectableItemBackground);
        }

        //设置TAB字体颜色的变化
        mViewPagerTabs.setOnPageChangeListener(mUserOnPageChangeListener);
        if ( !mViewPagerTabs.isDoubleLayerInfo[0] )
            ((TextView)((ViewGroup) mViewPagerTabs.getChildAt(0)).getChildAt(0)).setTextColor(getTextSelectedColor());
        else {
            ((TextView) ((ViewGroup) ((ViewGroup) mViewPagerTabs.getChildAt(0)).getChildAt(0)).getChildAt(0)).setTextColor(getTextSelectedColor());
            ((TextView) ((ViewGroup) ((ViewGroup) mViewPagerTabs.getChildAt(0)).getChildAt(0)).getChildAt(1)).setTextColor(getTextSelectedColor());
        }
    }

    public void updateViewPagerHandler(ViewPagerHandler viewPagerHandler) {
        if (viewPagerHandler == null)
            return;

        mViewPagerItems = viewPagerHandler.getViewPagerItems();
        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), mViewPagerItems);
        mViewPager.setAdapter(mViewPagerAdapter);
//        mViewPagerTabs.removeAllTabs();
        showTabs(mViewPager);
    }

    protected abstract int getLayoutResId();
    protected abstract boolean expandTabs();
    protected abstract int getTextColor();
    protected abstract int getTextSelectedColor();
    protected abstract int getIndicatorColor();
    protected abstract int getTabsBackgroundColor();
    protected abstract int getTabsTextPiexlSize();
    protected abstract void refreshData();
    protected abstract boolean allCaps();
}