package com.mozhuowen.rxandroid.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.mozhuowen.R;
import com.mozhuowen.rxandroid.ui.Toolbar;
import com.mozhuowen.rxandroid.ui.ToolbarDefault;
import com.mozhuowen.rxandroid.ui.handlers.ActionBarHandler;

/**
 * Created by Awen on 16/5/17.
 * Email:mozhuowen@gmail.com
 */
public abstract class BaseActivity extends AppCompatActivity {

    private final static String TOOLBAR_SEARCH_CONSTRAINT_KEY = "ToolbarSearchConstraint";
    private final static String TOOLBAR_SEARCH_IS_SHOWN = "ToolbarSearchIsShown";
    private static int statusbar_height;
    private static int actionBarHeight;

    private Toolbar mCustomToolbar;
    private View mShadowView;
    private View mCustomSearchButton;
    private ActionBarHandler mActionBarHandler;
    public boolean fullscreen = false;

    //友盟SESSION统计
    public void onResume() {
        super.onResume();
    }
    public void onPause() {
        super.onPause();
    }

    public void onCreate(Bundle savedInstanceState, int contentView) {
        super.onCreate(savedInstanceState);
        setContentView(contentView);

        // Toolbar Shadow View
        mShadowView = findViewById(R.id.toolbar_shadow);
        if ( mShadowView != null ) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mShadowView
                    .getLayoutParams();
            params.topMargin = (int) getResources()
                    .getDimension(R.dimen.mdl_viewpager_with_tabs_height);
        }
        if ( enableActionBarShadow() ) {
            showActionBarShadow();
        }else {
            hideActionBarShadow();
        }

        mActionBarHandler = getActionBarHandler();
        if (mActionBarHandler == null){
            mCustomToolbar = new ToolbarDefault(this);
        }
        else mCustomToolbar = mActionBarHandler.build();

        ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content))
                .getChildAt(0);
        rootView.addView(mCustomToolbar,new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));

        mCustomToolbar.getToolbar()
                .setTitleTextColor(getResources().getColor(R.color.actionbar_titilecolor));

        if (enableLayoutFullScreen()) {
            View view = this.findViewById(android.R.id.content);
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getActionbarHeight() /***+ getStatusBarHeight()*/);
            params.setMargins(0,getStatusBarHeight(),0,0);
            mCustomToolbar.setLayoutParams(params);
            mCustomToolbar.getToolbar().setBackgroundColor(getResources().getColor(android.R.color.transparent));
//            mCustomToolbar.setPadding(0, getStatusBarHeight(), 0, 0);

//            View statusview = ((ToolBarMain)mCustomToolbar).statusView;
//            statusview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight()));
//            statusview.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                // 透明状态栏
                getWindow().addFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setSupportActionBar(mCustomToolbar.getToolbar());
        if (getSupportActionBar() != null) {

            if (!enableBackArrow())
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            else {
                final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
                upArrow.setColorFilter(getResources().getColor(R.color.backarrow_color), PorterDuff.Mode.SRC_ATOP);
                getSupportActionBar().setHomeAsUpIndicator(upArrow);
            }

        }
    }

    // Calculate ActionBar height
    public int getActionbarHeight() {
        TypedValue tv = new TypedValue();
        if (actionBarHeight == 0) {
            if (getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
            }
        }
        return actionBarHeight;
    }
    //Calculate satausbar height
    public int getStatusBarHeight() {
        if (statusbar_height == 0 ) {
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusbar_height = getResources().getDimensionPixelSize(resourceId);
            }
            return statusbar_height;
        }

        return statusbar_height;
    }

    protected boolean enableLayoutFullScreen()    //actionbar下的LAYOUT全屏
    {
        return false;
    }

    public void showActionBarShadow() {
        if (mShadowView != null) mShadowView.setVisibility(View.VISIBLE);
    }


    public void hideActionBarShadow() {
        if (mShadowView != null) mShadowView.setVisibility(View.GONE);
    }


    protected abstract boolean enableActionBarShadow();
    protected abstract ActionBarHandler getActionBarHandler();
    protected abstract boolean enableBackArrow();
}
