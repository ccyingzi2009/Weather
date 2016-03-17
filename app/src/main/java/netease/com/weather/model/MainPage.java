package netease.com.weather.model;


import java.util.List;

/**
 * Created by user on 16-3-16.
 */
public class MainPage {
    private String title;
    private List<String> mCoverUrls;

    public MainPage(String title) {
        this.title = title;
    }

    public MainPage(List<String> mCoverUrls) {
        this.mCoverUrls = mCoverUrls;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getmCoverUrls() {
        return mCoverUrls;
    }

    public void setmCoverUrls(List<String> mCoverUrls) {
        this.mCoverUrls = mCoverUrls;
    }

    @Override
    public String toString() {
        return "MainPage{" +
                "mCoverUrls=" + mCoverUrls +
                '}';
    }
}
