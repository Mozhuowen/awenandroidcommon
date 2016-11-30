package com.mozhuowen.rxandroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mozhuowen.R;
import com.mozhuowen.rxandroid.presenter.BasePresenter;

import butterknife.ButterKnife;

/**
 * Created by Awen on 16/5/26.
 * Email:mozhuowen@gmail.com
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    protected static int statusbar_height;
    protected T presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);

        if (enableLayoutFullScreen()) {
            View mainContent = view.findViewById(R.id.main_content);
            FrameLayout.LayoutParams params =
                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT /***+ getStatusBarHeight()*/);
            params.setMargins(0,getStatusBarHeight(),0,0);
            mainContent.setLayoutParams(params);
        }

        initCreateView(view);
        initPresenter();
        return view;
    }

    protected abstract int getLayoutResId();
    protected abstract void initCreateView(View view);
    protected abstract void initPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public int pxToDp(int px) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public boolean enableLayoutFullScreen() {
        return false;
    }

    //Calculate satausbar height
    public int getStatusBarHeight() {
        if (statusbar_height == 0 ) {
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusbar_height = getResources().getDimensionPixelSize(resourceId);
            }
        }
//        Logger.d("Got statusbar_height size->"+statusbar_height);
        return statusbar_height;
    }
}
