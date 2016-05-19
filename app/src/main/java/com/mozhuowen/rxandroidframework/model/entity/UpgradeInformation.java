package com.mozhuowen.rxandroidframework.model.entity;

import java.io.Serializable;

/**
 * Created by Awen on 16/5/11.
 * Email:mozhuowen@gmail.com
 */
public class UpgradeInformation implements Serializable
{
    public String title;
    public String desc;
    public String url;
    public int newVersion;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(int newVersion) {
        this.newVersion = newVersion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
