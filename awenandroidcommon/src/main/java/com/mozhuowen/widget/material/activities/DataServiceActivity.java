package com.mozhuowen.widget.material.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import net.datafans.android.common.data.service.BaseResponse;
import net.datafans.android.common.data.service.DataService;
import net.datafans.android.common.data.service.DataServiceDelegate;

public abstract class DataServiceActivity extends ActivityDefault implements DataServiceDelegate
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataService();
    }

    @Override
    public void onStatusOk(BaseResponse response, DataService service) {

    }

    @Override
    public void onStatusError(BaseResponse response, DataService service) {
        Log.e("onStatusError",response.toString());
        Toast toast = Toast.makeText(this, response.getErrorMsg(),
                Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onRequestError(int errorCode, byte[] errorResponse, Throwable throwable, DataService service) {
        if (errorCode == -2) {
            Log.e("ANDROID_COMMON", "network exception");
            Toast toast = Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (errorCode == -1) {
            Log.e("ANDROID_COMMON", "data_parse_exception");
            Toast toast = Toast.makeText(this, "数据解析错误", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public abstract void initDataService();
}