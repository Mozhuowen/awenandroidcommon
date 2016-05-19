package com.mozhuowen.widget.views.stickyviewpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.mozhuowen.R;

public abstract class ScrollViewDefaultFragment extends StickHeaderScrollViewFragment
{

    @Override
    public ScrollView createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ScrollView scrollView = (ScrollView)inflater.inflate(R.layout.stl_fragment_scrollview, container, false);
        scrollView.addView(initViews(inflater,container,savedInstanceState));

        return scrollView;
    }

    public abstract View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}