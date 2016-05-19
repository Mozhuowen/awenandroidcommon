package com.mozhuowen.rxandroid.service.local;

import android.content.Context;
import android.content.SharedPreferences;


public abstract class GlobalSharedPreferenceStorageService {

    protected SharedPreferences getPreference(Context context,String type) {
        SharedPreferences preference = context.getSharedPreferences(type, Context.MODE_PRIVATE);
        return preference;
    }

    protected SharedPreferences.Editor getEditor(Context context,String type) {
        return getPreference(context,type).edit();
    }

}
