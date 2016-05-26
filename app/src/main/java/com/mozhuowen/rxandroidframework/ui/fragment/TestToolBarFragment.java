package com.mozhuowen.rxandroidframework.ui.fragment;

import android.view.View;

import com.mozhuowen.rxandroid.fragments.ToolBarFragment;
import com.mozhuowen.rxandroid.ui.handlers.ActionBarHandler;
import com.mozhuowen.rxandroid.ui.handlers.ActionBarHandlerDefault;
import com.mozhuowen.rxandroidframework.R;

/**
 * Created by Awen on 16/5/26.
 * Email:mozhuowen@gmail.com
 */
public class TestToolBarFragment extends ToolBarFragment {

    @Override
    protected boolean enableActionBar() {
        return true;
    }

    @Override
    protected ActionBarHandler getActionBarHandler() {
        return new ActionBarHandlerDefault(getActivity());
    }

    @Override
    protected String getTitle() {
        return getArguments().getString("content");
    }

    @Override
    protected int getMenuResid() {
        return 0;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initCreateView(View view) {

    }

    @Override
    protected void initPresenter() {

    }
}
