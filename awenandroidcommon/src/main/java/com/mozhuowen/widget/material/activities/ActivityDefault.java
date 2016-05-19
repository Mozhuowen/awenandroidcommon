package com.mozhuowen.widget.material.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

public abstract class ActivityDefault extends AAActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, getContentResId());

        ActionBar actionBar = getSupportActionBar();
        if (isActionbarVisible()) {
            actionBar.setTitle(getTitleString());
            actionBar.setDisplayHomeAsUpEnabled(isDisplayHomeAsUpEnabled());
//            final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//            upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
//            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else
            actionBar.hide();



        initViews(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return true;
        }
    }

    public abstract int getContentResId();
    public abstract String getTitleString();
    public abstract void initViews(Bundle savedInstanceState);
    public abstract boolean isActionbarVisible();
    public abstract boolean isDisplayHomeAsUpEnabled();
}