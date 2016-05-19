package com.mozhuowen.rxandroidframework.model;


import com.mozhuowen.rxandroidframework.model.entity.Gank;

import java.util.List;

/**
 * 休息视频数据模型
 * Created by xybcoder on 2016/3/1.
 */
public class FunnyData extends BaseData{
    public List<Gank> results;

    @Override
    public String toString() {
        return "FunnyData{" +
                "results=" + results +
                '}';
    }
}
