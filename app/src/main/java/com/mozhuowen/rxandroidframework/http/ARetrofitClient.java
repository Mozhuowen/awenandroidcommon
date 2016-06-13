package com.mozhuowen.rxandroidframework.http;

import com.mozhuowen.rxandroid.service.http.BaseRetrofitClient;
import com.mozhuowen.rxandroidframework.BuildConfig;

/**
 * Created by Awen on 16/5/10.
 * Email:mozhuowen@gmail.com
 */
public class ARetrofitClient extends BaseRetrofitClient<ARetrofit> {

    private static ARetrofit retrofit;

    private ARetrofitClient() {}


    public static ARetrofit getRetrofitInstance() {
        if ( retrofit == null) {
//            retrofit =  new ARetrofitClient().getBaseClient("release.crazyfit.appcomeon.com","/",ARetrofit.class, BuildConfig.DEBUG);
//            retrofit =  new ARetrofitClient().getBaseClient("gank.io","/api/",ARetrofit.class, BuildConfig.DEBUG);
            retrofit =  new ARetrofitClient().getBaseClient("192.168.31.191:5000","/",ARetrofit.class, BuildConfig.DEBUG);
        }

        return retrofit;
    }

    @Override
    protected void setExtendInterceptor() {
//        ExtendInterceptor interceptor = new HttpServiceConfig();
//        this.extendInterceptor = interceptor;
    }
}
