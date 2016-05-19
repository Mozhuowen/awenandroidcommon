package com.mozhuowen.widget.material.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.blunderer.materialdesignlibrary.activities.AActivity;
import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.blunderer.materialdesignlibrary.handlers.ActionBarSearchHandler;
import com.blunderer.materialdesignlibrary.views.Toolbar;
import com.blunderer.materialdesignlibrary.views.ToolbarDefault;
import com.blunderer.materialdesignlibrary.views.ToolbarSearch;
import com.mozhuowen.R;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Awen on 16/1/14.
 * Email:mozhuowen@gmail.com
 * 重构 com.blunderer.materialdesignlibrary.activities.AActivity
 */
public abstract class AAActivity extends AActivity {

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
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void onCreate(Bundle savedInstanceState, int contentView) {
        super.onCreate(savedInstanceState);
        setContentView(contentView);

        // Toolbar Shadow View
        mShadowView = findViewById(R.id.toolbar_shadow);
        if (mShadowView != null && (this instanceof ViewPagerWithTabsActivity)) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mShadowView
                    .getLayoutParams();
            params.topMargin = (int) getResources()
                    .getDimension(R.dimen.mdl_viewpager_with_tabs_height);
        }
        if (enableActionBarShadow()) {
            showActionBarShadow();
        }else {
            hideActionBarShadow();
        }

        mActionBarHandler = getActionBarHandler();
        if (mActionBarHandler == null){
            mCustomToolbar = new ToolbarDefault(this);
        }
        else mCustomToolbar = mActionBarHandler.build();

        // Toolbar Search
        if (mCustomToolbar instanceof ToolbarSearch) {
            ToolbarSearch toolbarSearch = (ToolbarSearch) mCustomToolbar;
            toolbarSearch.setActivity(this);

            if (savedInstanceState != null) {
                toolbarSearch
                        .setConstraint(savedInstanceState.getString(TOOLBAR_SEARCH_CONSTRAINT_KEY));
                if (savedInstanceState.getBoolean(TOOLBAR_SEARCH_IS_SHOWN)) {
                    toolbarSearch.showSearchBar();
                    hideActionBarShadow();
                }
            }
        }

        ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content))
                .getChildAt(0);
        if (this instanceof NavigationDrawerActivity) rootView.addView(mCustomToolbar, 1);
        else rootView.addView(mCustomToolbar);

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
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            upArrow.setColorFilter(getResources().getColor(R.color.back_color), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Custom Search Button
        if (mActionBarHandler instanceof ActionBarSearchHandler) {
            int customSearchButtonId = ((ActionBarSearchHandler) mActionBarHandler)
                    .getCustomSearchButtonId();
            if (customSearchButtonId != 0) mCustomSearchButton = findViewById(customSearchButtonId);
            else mCustomSearchButton = ((ActionBarSearchHandler) mActionBarHandler)
                    .getCustomSearchButton();
            if (mCustomSearchButton != null) {
                mCustomSearchButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        showActionBarSearch();
                        hideActionBarShadow();
                    }

                });
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mCustomToolbar instanceof ToolbarSearch) {
            ToolbarSearch toolbarSearch = (ToolbarSearch) mCustomToolbar;
            outState.putBoolean(TOOLBAR_SEARCH_IS_SHOWN, toolbarSearch.isSearchBarShown());
            outState.putString(TOOLBAR_SEARCH_CONSTRAINT_KEY, toolbarSearch.getConstraint());
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mCustomToolbar instanceof ToolbarSearch && mCustomSearchButton == null) {
            MenuItem searchItem = menu
                    .add(0, R.id.mdl_toolbar_search_menu_item, Menu.NONE, "Search")
                    .setIcon(R.drawable.ic_action_search);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            }
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.mdl_toolbar_search_menu_item) {
            showActionBarSearch();
            hideActionBarShadow();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mCustomToolbar instanceof ToolbarSearch) {
            ToolbarSearch toolbarSearch = ((ToolbarSearch) mCustomToolbar);
            if (toolbarSearch.isSearchBarShown()) {
                toolbarSearch.hideSearchBar();
                showActionBarShadow();
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ToolbarSearch.SEARCH_REQUEST_CODE) {
            ((ToolbarSearch) mCustomToolbar).onActivityResult(requestCode, resultCode, data);
        }
    }

    public android.support.v7.widget.Toolbar getMaterialDesignActionBar() {
        return mCustomToolbar.getToolbar();
    }

    public View getActionBarShadowView() {
        return mShadowView;
    }


    public void showActionBarSearch() {
        if (mCustomToolbar instanceof ToolbarSearch) {
            ((ToolbarSearch) mCustomToolbar).showSearchBar();
        }
    }

    public void hideActionBarSearch() {
        if (mCustomToolbar instanceof ToolbarSearch) {
            ((ToolbarSearch) mCustomToolbar).hideSearchBar();
        }
    }

    public void showActionBarShadow() {
        if (mShadowView != null) mShadowView.setVisibility(View.VISIBLE);
    }


    public void hideActionBarShadow() {
        if (mShadowView != null) mShadowView.setVisibility(View.GONE);
    }

    protected boolean enableLayoutFullScreen()    //actionbar下的LAYOUT全屏
    {
        return false;
    }


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

    // Calculate ActionBar height
    public int getActionbarHeight() {
        TypedValue tv = new TypedValue();
        if (actionBarHeight == 0) {
            if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
            }
        }
        return actionBarHeight;
    }

    public void setToolBarTransparent() {
//        mCustomToolbar.getToolbar().setBackgroundColor(getResources().getColor(android.R.color.transparent));
        mCustomToolbar.getToolbar().getBackground().setAlpha(0);
    }

    public void setToolBarAlpha(int alpha) {
        mCustomToolbar.getToolbar().getBackground().setAlpha(alpha);
    }

    public void setToolBarContentColor(int color) {
        if (color == getResources().getColor(android.R.color.transparent))
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            upArrow.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        mCustomToolbar.getToolbar().setTitleTextColor(color);
    }


    protected abstract boolean enableActionBarShadow();

    protected abstract ActionBarHandler getActionBarHandler();
}
