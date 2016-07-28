package com.mozhuowen.rxandroid.service.http;

import java.io.IOException;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Awen on 16/6/24.
 * Email:mozhuowen@gmail.com
 */
public abstract class AddExtraDataInterceptor implements Interceptor {

    protected Map<String,String> urlParams;
    protected Map<String,String> headers;

    @Override
    public Response intercept(Chain chain) throws IOException {
        boolean needNewRequest = false;
        urlParams = getUrlParams();
        headers = getExtraHeaders();

        Request request = chain.request();
        HttpUrl.Builder newUrlBuilder = request.url().newBuilder();
        Request.Builder newRequestBuilder = request.newBuilder();

        if (urlParams != null && urlParams.size() > 0) {
            needNewRequest = true;
            for (Map.Entry<String,String> entry:urlParams.entrySet()) {
                newUrlBuilder.addQueryParameter(entry.getKey(),entry.getValue());
            }
        }

        if (headers != null && headers.size() > 0) {
            needNewRequest = true;
            newRequestBuilder
                    .url(newUrlBuilder.build())
                    .headers(request.headers())
                    .method(request.method(),request.body());
            for (Map.Entry<String,String> entry:headers.entrySet()) {
                newRequestBuilder.header(entry.getKey(),entry.getValue());
            }

        }

        if (needNewRequest)
            return chain.proceed(newRequestBuilder.build());
        else
            return chain.proceed(request);
    }

    public abstract Map<String,String> getUrlParams();
    public abstract Map<String,String> getExtraHeaders();
}
