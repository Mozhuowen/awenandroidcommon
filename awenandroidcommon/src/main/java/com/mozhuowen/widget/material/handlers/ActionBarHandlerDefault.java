package com.mozhuowen.widget.material.handlers;

import android.content.Context;

import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.blunderer.materialdesignlibrary.views.Toolbar;
import com.mozhuowen.widget.material.views.ToolBarMain;

public class ActionBarHandlerDefault extends ActionBarHandler
{

    public ActionBarHandlerDefault(Context context) {
        super(context);
    }

    @Override
    public Toolbar build() {
        return new ToolBarMain(mContext);
    }
}