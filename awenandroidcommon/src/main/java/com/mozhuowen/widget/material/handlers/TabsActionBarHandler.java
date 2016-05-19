package com.mozhuowen.widget.material.handlers;

import android.content.Context;

import com.astuetz.PagerSlidingTabStrip;
import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.blunderer.materialdesignlibrary.views.Toolbar;
import com.mozhuowen.widget.material.views.TabsToolBar;

/**
 * Created by Awen on 16/3/24.
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
