package com.mozhuowen.widget.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.orhanobut.logger.Logger;

/**
 * Created by Awen on 16/8/11.
 * Email:mozhuowen@gmail.com
 */
public class NestViewPager extends ViewPager {
    public NestViewPager(Context context) {
        super(context);
    }

    public NestViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if(v != this && v instanceof ViewPager) {
            Logger.d("return true");
            return true;
        }
        boolean result = super.canScroll(v, checkV, dx, x, y);
        Logger.d("return "+result);
        return result;
    }
}
