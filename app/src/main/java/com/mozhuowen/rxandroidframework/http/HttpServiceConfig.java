package com.mozhuowen.rxandroidframework.http;

import com.mozhuowen.rxandroid.service.http.ExtendInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Awen on 16/5/11.
 * Email:mozhuowen@gmail.com
 */
public class HttpServiceConfig extends ExtendInterceptor {

    @Override
    public Map<String,String> getExtraParams() {
        Map<String,String> params = new HashMap<>();
        params.put("user_id","0");
        params.put("version_code","3");
        params.put("api_version","2");
        params.put("platform","2");

        return params;
    }
}
