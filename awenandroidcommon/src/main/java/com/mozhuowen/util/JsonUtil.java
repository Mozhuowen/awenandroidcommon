package com.mozhuowen.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Awen on 16/6/13.
 * Email:mozhuowen@gmail.com
 */
public class JsonUtil {
    Gson gson;

    public static JsonUtil instance;

    public static JsonUtil getInstance() {
        if (instance == null ) {
            instance = new JsonUtil();
        }

        return instance;
    }

    private JsonUtil() {
        gson = new GsonBuilder().create();
    }

    public String JsontoString(Object o) {
        return gson.toJson(o);
    }

    public <T> T stringToObject(String str,Class<T> clazOFT) {
        return gson.fromJson(str,clazOFT);
    }
}
