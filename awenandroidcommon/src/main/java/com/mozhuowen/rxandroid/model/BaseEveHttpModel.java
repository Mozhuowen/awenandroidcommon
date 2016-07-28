package com.mozhuowen.rxandroid.model;

import com.mozhuowen.util.JsonUtil;

import java.io.Serializable;

/**
 * Created by Awen on 16/6/7.
 * Email:mozhuowen@gmail.com
 */
public class BaseEveHttpModel implements Serializable {

    public BaseEveHttpModel(){}

    public String _updated;
    public boolean _deleted;
    public String _created;
    public String _status;
    public String _id;
    public String _etag;
    public Meta _meta;
    public Links _links;
    private String nextPage;
    private String prePage = "1";

    public static class Meta implements Serializable
    {
        public Meta(){}
        public int max_results;
        public int total;
        public int page;
    }

    public static class Link implements Serializable
    {
        public Link(){}
        public String href;
        public String title;
    }

    public static class Links implements Serializable
    {
        public Links(){}
        public Link self;
        public Link last;
        public Link parent;
        public Link next;
        public Link collection;
    }

    public String toString() {
        return JsonUtil.getInstance().JsontoString(this);
    }

    public BaseEveHttpModel toAddObject() {
        this._id = null;
        return this;
    }

    public BaseEveHttpModel toUpdateObject() {
        return this;
    }

    public String getNextPage() {
        if (_links.next != null && _links.next.href.contains("?")) {
            String[] strs = _links.next.href.split("\\?")[1].split("&");
            for(String str:strs) {
                if (str.contains("page"))
                    this.nextPage = str.substring(5);
            }
            return this.nextPage;
        }
        return "1";
    }

    public boolean hasNext() {
        return _links.next != null;
    }

    public String getPrePage() {
        return "1";
    }


    public String get_created() {
        return _created;
    }

    public void set_created(String _created) {
        this._created = _created;
    }

    public boolean is_deleted() {
        return _deleted;
    }

    public void set_deleted(boolean _deleted) {
        this._deleted = _deleted;
    }

    public String get_etag() {
        return _etag;
    }

    public void set_etag(String _etag) {
        this._etag = _etag;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Meta get_meta() {
        return _meta;
    }

    public void set_meta(Meta _meta) {
        this._meta = _meta;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public String get_updated() {
        return _updated;
    }

    public void set_updated(String _updated) {
        this._updated = _updated;
    }
}
