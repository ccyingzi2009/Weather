package netease.com.weather.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 16-10-27.
 */

public class CommentResponseBean {

    /**
     * ajax_code : 0406
     * list : [{"text":"版面:贴图(Picture)","url":"/board/Picture"},
     * {"text":"主题:今天同学在帝都被骗了",
     * "url":"/article/Picture/1441163"},
     * {"text":"水木社区","url":"/mainpage"}]
     * default : /board/Picture
     * ajax_st : 1
     * ajax_msg : 发表成功
     */

    private String ajax_code;
    @SerializedName("default")
    private String defaultX;
    private int ajax_st;
    private String ajax_msg;
    /**
     * text : 版面:贴图(Picture)
     * url : /board/Picture
     */

    private List<ListEntity> list;

    public String getAjax_code() {
        return ajax_code;
    }

    public void setAjax_code(String ajax_code) {
        this.ajax_code = ajax_code;
    }

    public String getDefaultX() {
        return defaultX;
    }

    public void setDefaultX(String defaultX) {
        this.defaultX = defaultX;
    }

    public int getAjax_st() {
        return ajax_st;
    }

    public void setAjax_st(int ajax_st) {
        this.ajax_st = ajax_st;
    }

    public String getAjax_msg() {
        return ajax_msg;
    }

    public void setAjax_msg(String ajax_msg) {
        this.ajax_msg = ajax_msg;
    }

    public List<ListEntity> getList() {
        return list;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public static class ListEntity {
        private String text;
        private String url;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
