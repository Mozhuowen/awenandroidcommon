package com.mozhuowen.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Awen on 16/9/6.
 * Email:mozhuowen@gmail.com
 */
public class PermissionUtil {

    public static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 300;
    public static final int CAMERA_REQUEST_CODE = 301;
    public static final int CALL_PHONE = 302;

    public static boolean checkWriteStorage(Activity context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            return false;
        }
        return true;
    }


    public static boolean checkCamera(Activity context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA},
                    CAMERA_REQUEST_CODE);
            return false;
        }

        return true;
    }

    public static boolean checkPhone(Activity context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE},
                    CALL_PHONE);
            return false;
        }

        return true;
    }
}
