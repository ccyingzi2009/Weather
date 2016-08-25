package netease.com.weather.data.model;

import java.util.ArrayList;

/**
 * Created by liu_shuai on 16/8/22.
 */
public class ArticleBean {

    String content;
    String user;
    private ArrayList<String> imgs;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ArrayList<String> getImgs() {
        return imgs;
    }

    public void setImgs(ArrayList<String> imgs) {
        this.imgs = imgs;
    }
}
