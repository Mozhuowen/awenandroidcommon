package com.mozhuowen.rxandroid.ui.handlers;

import android.content.Context;

import com.mozhuowen.rxandroid.ui.Toolbar;


/**
 * Created by Awen on 16/5/17.
 * Email:mozhuowen@gmail.com
 */
public abstract class ActionBarHandler {

    protected final Context mContext;

    public ActionBarHandler(Context context) {
        mContext = context;
    }

    public abstract Toolbar build();

}
