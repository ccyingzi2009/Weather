package netease.com.weather.data.model;

import java.util.ArrayList;

/**
 * Created by liu_shuai on 16/8/22.
 */
public class ArticleSingleBean {

    String content;
    String user;
    private ArrayList<String> imgs;
    String quote;

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

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}
