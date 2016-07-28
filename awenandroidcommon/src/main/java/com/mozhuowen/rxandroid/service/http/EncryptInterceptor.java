package com.mozhuowen.rxandroid.service.http;

import com.mozhuowen.util.LogUtil;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by Awen on 16/6/22.
 * Email:mozhuowen@gmail.com
 */
public abstract class EncryptInterceptor implements Interceptor {

    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Response response;
        Request request = chain.request();
        HttpUrl originalHttpUrl = request.url();
        String bodyToString = bodyToString(request.body());

        if (bodyToString != null && bodyToString.length() > 0) {

            String enBody = getEncryptString(bodyToString);

            HttpUrl.Builder newbuilder = originalHttpUrl.newBuilder();
            HttpUrl newurl = newbuilder.build();
            Request.Builder requestBuilder = request.newBuilder()
                    .url(newurl)
                    .headers(request.headers())
                    .method(request.method(), RequestBody.create(MediaType.parse("application/json; charset=utf-8"), enBody));

            response = chain.proceed(requestBuilder.build());

        } else
            response = chain.proceed(request);

        if (!response.request().url().host().equals("192.168.31.191"))
            return response;

        BufferedSource source = response.body().source();
        source.request(Long.MAX_VALUE);
        Buffer resBuffer = source.buffer().clone();
        String resString = resBuffer.readUtf8();
        String destr = getDecryptString(resString);

        Response.Builder responseBuilder = response.newBuilder()
                .headers(response.headers())
                .header("Content-Length",destr.length()+"")
                .header("Content-Type","application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"),destr));

        return responseBuilder.build();
    }

    private static String bodyToString(final RequestBody requestBody) {
        try {
            final Buffer buffer = new Buffer();
            if(requestBody != null)
                requestBody.writeTo(buffer);
            else {
                LogUtil.e("bodyToString requestBody is null!");
                return "";
            }
            return buffer.readUtf8();
        }catch(IOException e){
            LogUtil.e("bodyToString IOException!");
            return "";
        }
    }

    protected abstract String getEncryptString(String bodyString);
    protected abstract String getDecryptString(String resString);
}
