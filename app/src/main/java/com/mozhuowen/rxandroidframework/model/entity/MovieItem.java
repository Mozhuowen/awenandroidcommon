package com.mozhuowen.rxandroidframework.model.entity;

import com.mozhuowen.rxandroid.model.BaseEveHttpModel;

import java.util.List;

/**
 * Created by Awen on 16/6/7.
 * Email:mozhuowen@gmail.com
 */
public class MovieItem extends BaseEveHttpModel {
    public MovieItem(){}

    public String title;
    public String fuck;
    public String url_detail;
    public String desc;
    public List<String> image_urls;
    public List<Image> images;

    public static class Image
    {
        public String url;
        public String path;
        public String checksum;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFuck() {
        return fuck;
    }

    public void setFuck(String fuck) {
        this.fuck = fuck;
    }

    public List<String> getImage_urls() {
        return image_urls;
    }

    public void setImage_urls(List<String> image_urls) {
        this.image_urls = image_urls;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl_detail() {
        return url_detail;
    }

    public void setUrl_detail(String url_detail) {
        this.url_detail = url_detail;
    }
}
