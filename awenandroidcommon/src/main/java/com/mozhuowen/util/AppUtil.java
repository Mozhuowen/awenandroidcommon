package com.mozhuowen.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.util.Log;

public class AppUtil {

    private static final Object Monitor = new Object();
    private static AppUtil instance;
    private static Context context;
    private static PackageInfo packageInfo;
    private static ApplicationInfo applicationInfo;

    private AppUtil() {}

    public static AppUtil getInstance(Context mcontext) {
        if (instance == null ) {
            synchronized (Monitor) {
                context = mcontext;
                instance = new AppUtil();
                try {
                    packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                    applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e(LogUtil.TAG, e.toString());
                }
            }
        }
        return instance;
    }

    public String getVersionName() {
        if (packageInfo == null) return "";
        return packageInfo.versionName;
    }

    public int getVersionCode() {
        if (packageInfo == null) return 0;
        return packageInfo.versionCode;
    }

    public String getMarketId() {
        if (applicationInfo == null) return "";
        return applicationInfo.metaData.getString("UMENG_CHANNEL");
    }

    public String getDeviceId() {
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return  android_id;
    }
}
