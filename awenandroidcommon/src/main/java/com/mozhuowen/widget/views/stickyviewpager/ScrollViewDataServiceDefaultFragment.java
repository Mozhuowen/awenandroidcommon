package com.mozhuowen.widget.views.stickyviewpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ScrollView;

import net.datafans.android.common.data.service.DataServiceDelegate;

public abstract class ScrollViewDataServiceDefaultFragment extends ScrollViewDefaultFragment implements DataServiceDelegate
{
    @Override
    public ScrollView createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ScrollView scrollView = super.createView(inflater,container,savedInstanceState);
        setDataService();

        return scrollView;
    }

    public abstract void setDataService();
}