package com.mozhuowen.rxandroidframework.http;

import com.mozhuowen.rxandroid.model.BaseEveHttpModel;
import com.mozhuowen.rxandroid.model.EveListHttpModel;
import com.mozhuowen.rxandroidframework.model.FunnyData;
import com.mozhuowen.rxandroidframework.model.GanHuoData;
import com.mozhuowen.rxandroidframework.model.GankData;
import com.mozhuowen.rxandroidframework.model.HttpResult;
import com.mozhuowen.rxandroidframework.model.MeiziData;
import com.mozhuowen.rxandroidframework.model.entity.MovieItem;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Awen on 16/5/10.
 * Email:mozhuowen@gmail.com
 */
public interface ARetrofit
{
    public final String HEADERJSON = "application/json";

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
    //testEve
    @GET("/movies")
    Observable<EveListHttpModel<MovieItem>> getMovie();

    @GET("/movies/{id}")
    Observable<MovieItem> getOneMovie(@Path("id") String id);

    @POST("/movies")
    Observable<BaseEveHttpModel> addOneData(@Body MovieItem movieItem);

    @Headers("Content-Type: application/json")
    @PATCH("/movies/{id}")
    Observable<BaseEveHttpModel> updateOneMovie(@Path("id") String id, @Header("If-Match") String etag,  @Body MovieItem movieItem);
}
