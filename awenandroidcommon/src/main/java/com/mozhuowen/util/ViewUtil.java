package com.mozhuowen.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Awen on 16/7/3.
 * Email:mozhuowen@gmail.com
 */
public class ViewUtil {

    // Calculate ActionBar height
    public static int getActionbarHeight(Context context) {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (actionBarHeight == 0) {
            if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
            }
        }
        return actionBarHeight;
    }
    //Calculate satausbar height
    public static int getStatusBarHeight(Context context) {
        int statusbar_height = 0;
        if (statusbar_height == 0 ) {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusbar_height = context.getResources().getDimensionPixelSize(resourceId);
            }
            return statusbar_height;
        }

        return statusbar_height;
    }

}
