package netease.com.weather.data.model;

/**
 * Created by liu_shuai on 16/3/17.
 */
public class MainSlider {
    private String href;
    private String title;
    private String src;

    public MainSlider(String href, String src, String title) {
        this.href = href;
        this.src = src;
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "MainSlider{" +
                "href='" + href + '\'' +
                ", title='" + title + '\'' +
                ", src='" + src + '\'' +
                '}';
    }
}
