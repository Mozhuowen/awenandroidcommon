package com.mozhuowen.rxandroid.service.http;

/**
 * Created by Awen on 16/7/4.
 * Email:mozhuowen@gmail.com
 */
public class EveParamUtil {

    public static String getMovieWhere(String code,EveConfig.MovieSearchType e) {

        switch (e) {
            case ACTRESS:
                return whereActress(code);
            case PUBLISHER:
                break;
            case MAKER:
                break;
            case SERIES:
                break;
            case GENRE:
                break;
        }
        return null;
    }

    public static String whereSeries(String code) {
        String param = "{\"series.code\":\"%s\"}";
        return doFormat(param,code);
    }

    public static String wherePublisher(String code) {
        String param = "{\"publisher.code\":\"%s\"}";
        return doFormat(param,code);
    }

    public static String whereActress(String code) {
        String param = "{\"actress\":{\"$elemMatch\":{\"code\":\"%s\"}}}";
        return doFormat(param,code);
    }

    public static String doFormat(String sourceStr,String[] strs) {
        return String.format(sourceStr,strs);
    }

    public static String doFormat(String sourceStr,String str) {
        return String.format(sourceStr,str);
    }

}
