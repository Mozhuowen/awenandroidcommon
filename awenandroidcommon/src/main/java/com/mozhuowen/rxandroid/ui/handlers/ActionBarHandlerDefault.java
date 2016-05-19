package com.mozhuowen.rxandroid.ui.handlers;

import android.content.Context;

import com.mozhuowen.rxandroid.ui.Toolbar;
import com.mozhuowen.rxandroid.ui.ToolbarDefault;

public class ActionBarHandlerDefault extends ActionBarHandler
{
    public ActionBarHandlerDefault(Context context) {
        super(context);
    }

    @Override
    public Toolbar build() {
        return new ToolbarDefault(mContext);
    }
}