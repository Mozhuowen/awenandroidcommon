package com.mozhuowen.widget.material.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.blunderer.materialdesignlibrary.views.Toolbar;
import com.mozhuowen.R;

/**
 * Created by Awen on 16/3/24.
 * Email:mozhuowen@gmail.com
 */
public class TabsToolBar extends Toolbar {

    public View statusView;
    protected LayoutInflater inflater;
    protected PagerSlidingTabStrip pagerSlidingTabStrip;

    public TabsToolBar(Context context) {
        this(context, null);
    }

    public TabsToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabsToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.mdl_toolbar_default, this, true);

        mToolbar = (android.support.v7.widget.Toolbar) getChildAt(0);
        inflater.inflate(R.layout.actionbar_tabs, this, true);
        pagerSlidingTabStrip = (PagerSlidingTabStrip) getChildAt(1);
    }

    public android.support.v7.widget.Toolbar getToolbar() {
        return mToolbar;
    }

    public PagerSlidingTabStrip getPagerSlidingTabStrip() {
        return pagerSlidingTabStrip;
    }
}
