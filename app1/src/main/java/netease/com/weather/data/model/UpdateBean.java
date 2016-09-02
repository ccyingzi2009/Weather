package netease.com.weather.data.model;

/**
 * Created by user on 16-9-2.
 */
public class UpdateBean {

    /**
     * app_version : 1.0
     * title : 1.0更新
     * content : bugfix
     * type : update
     */

    private String app_version;
    private String title;
    private String content;
    private String type;
    private int versionCode;
    private String url;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
