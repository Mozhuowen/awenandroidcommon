package com.mozhuowen.rxandroidframework.http;

import com.mozhuowen.rxandroidframework.model.FunnyData;
import com.mozhuowen.rxandroidframework.model.GanHuoData;
import com.mozhuowen.rxandroidframework.model.GankData;
import com.mozhuowen.rxandroidframework.model.HttpResult;
import com.mozhuowen.rxandroidframework.model.MeiziData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Awen on 16/5/10.
 * Email:mozhuowen@gmail.com
 */
public interface ARetrofit
{
    // http://gank.io/api/data/数据类型/请求个数/第几页
    @GET(value = "data/福利/" + 20 + "/{page}")
    Observable<MeiziData> getMeiziData(@Path("page") int page);
    @GET("data/休息视频/" + 20 + "/{page}")
    Observable<FunnyData> getFunnyData(@Path("page") int page);

    //请求某天干货数据
    @GET("day/{year}/{month}/{day}")
    Observable<GankData> getDailyData(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day);

    //请求不同类型干货（通用）
    @GET("data/{type}/"+20+"/{page}")
    Observable<GanHuoData> getGanHuoData(@Path("type") String type, @Path("page") int page);

    //mygetrequest
    @GET("/system/upgrade/")
    Observable<HttpResult> getUpgradeInfo();
}
