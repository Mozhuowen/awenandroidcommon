package com.mozhuowen.rxandroid.service.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseRetrofitClient<T>
{
    private static String HOST;
    private static String BASEURL;
    private static final Object monitor = new Object();
    protected static Retrofit retrofit;

    private T baseRetrofit;

    private void init(boolean ifLogging) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();

        //手动创建一个OkHttpClient并设置超时和重试
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.retryOnConnectionFailure(true);

        if (getExtendInterceptor() != null && getExtendInterceptor().size() > 0) {

            for (Interceptor interceptor:getExtendInterceptor()) {
                httpClientBuilder.addInterceptor(interceptor);
            }

        }

        if (ifLogging) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            httpClientBuilder.addInterceptor(logging);
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public T getBaseClient(String domain,String basePath,Class retrofitclass,boolean ifLogging) {
        synchronized (monitor) {
            if (baseRetrofit == null ){
                HOST = "http://"+domain;
                BASEURL = HOST + basePath;
                init(ifLogging);
                baseRetrofit = (T)retrofit.create(retrofitclass);
            }
            return baseRetrofit;
        }
    }

    protected abstract List<Interceptor> getExtendInterceptor();

}