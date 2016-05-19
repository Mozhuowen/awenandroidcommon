package com.mozhuowen.rxandroidframework.model.entity;

import java.io.Serializable;

/**
 * Created by Awen on 16/5/11.
 * Email:mozhuowen@gmail.com
 */
public class UpgradeInfo implements Serializable {

    public UpgradeInformation info;


    public UpgradeInformation getInfo() {
        return info;
    }

    public void setInfo(UpgradeInformation info) {
        this.info = info;
    }
}
