package com.mozhuowen.rxandroid.ui.handlers;

import android.content.Context;

import com.mozhuowen.rxandroid.ui.Toolbar;
import com.mozhuowen.rxandroid.ui.ToolbarDefault;

//import android.support.v7.widget.Toolbar;

/**
 * Created by Awen on 16/10/12.
 * Email:mozhuowen@gmail.com
 */

public class ActionBarWhiteHandler extends ActionBarHandler {
    ToolbarDefault toolbar;

    public ActionBarWhiteHandler(Context context) {
        super(context);
    }

    @Override
    public Toolbar build() {
        toolbar = new ToolbarDefault(mContext);
        toolbar.getToolbar().setBackgroundColor(mContext.getResources().getColor(android.R.color.white));

        return toolbar;
    }
}
