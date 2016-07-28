package com.mozhuowen.rxandroid.service.http;

import java.io.IOException;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Awen on 16/5/11.
 * Email:mozhuowen@gmail.com
 */
public abstract class ExtendInterceptor implements Interceptor {

    protected Map<String,String> extraParams;

    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        extraParams = getExtraParams();

        Request request = chain.request();
        HttpUrl originalHttpUrl = request.url();

        if (extraParams != null && extraParams.size() > 0) {
            HttpUrl.Builder newbuilder = originalHttpUrl.newBuilder();
            for(Map.Entry<String,String> entry:extraParams.entrySet()) {
                newbuilder.addQueryParameter(entry.getKey(),entry.getValue());
            }
            HttpUrl newurl = newbuilder.build();
            Request.Builder requestBuilder = request.newBuilder()
                    .url(newurl)
                    .headers(request.headers())
                    .method(request.method(), request.body());
            return chain.proceed(requestBuilder.build());
        } else
            return chain.proceed(request);

    }

    public abstract Map<String,String> getExtraParams();
}
