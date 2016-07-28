package com.mozhuowen.rxandroid.model;

import com.mozhuowen.util.JsonUtil;

import java.io.Serializable;

/**
 * Created by Awen on 16/5/11.
 * Email:mozhuowen@gmail.com
 */
public class BaseHttpModel<T> implements Serializable {
    public int status;
    public String message;

    public T data;

    public String toString() {
        return JsonUtil.getInstance().JsontoString(this);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
