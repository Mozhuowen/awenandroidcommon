package com.mozhuowen.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

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

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
