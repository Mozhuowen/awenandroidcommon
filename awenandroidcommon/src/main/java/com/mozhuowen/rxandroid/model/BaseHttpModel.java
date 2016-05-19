package com.mozhuowen.rxandroid.model;

import java.io.Serializable;

/**
 * Created by Awen on 16/5/11.
 * Email:mozhuowen@gmail.com
 */
public class BaseHttpModel<T> implements Serializable {
    public int status;

    public T data;
}
