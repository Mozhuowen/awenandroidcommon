package com.mozhuowen.rxandroid.ui.handlers;

import android.content.Context;

import com.astuetz.PagerSlidingTabStrip;
import com.mozhuowen.rxandroid.ui.TabsToolBar;
import com.mozhuowen.rxandroid.ui.Toolbar;

/**
 * Created by Awen on 16/5/17.
 * Email:mozhuowen@gmail.com
 */
public class TabsActionBarHandler extends ActionBarHandler {
    TabsToolBar tabsToolBar;

    public TabsActionBarHandler(Context context) {
        super(context);
    }

    @Override
    public Toolbar build() {
        if (tabsToolBar == null)
            tabsToolBar = new TabsToolBar(mContext);

        return tabsToolBar;
    }

    public PagerSlidingTabStrip getPagerSlidingTabStrip() {
        if (tabsToolBar == null)
            build();

        return tabsToolBar.getPagerSlidingTabStrip();
    }
}
