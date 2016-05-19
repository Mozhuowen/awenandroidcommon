package com.mozhuowen.rxandroidframework.model;


import com.mozhuowen.rxandroidframework.model.entity.Meizi;

import java.util.List;

/**
 * 福利数据模型
 * Created by xybcoder on 2016/3/1.
 * 数据格式见：http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1
 */
public class MeiziData extends BaseData {
    public List<Meizi> results;

    @Override
    public String toString() {
        return "MeiziData{" +
                "results=" + results +
                '}';
    }
}
