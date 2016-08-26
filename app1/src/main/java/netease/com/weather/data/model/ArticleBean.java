package netease.com.weather.data.model;

import java.util.List;

/**
 * Created by liu_shuai on 16/8/22.
 */
public class ArticleBean {

    String title;
    List<ArticleSingleBean> mSingleBeanList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ArticleSingleBean> getSingleBeanList() {
        return mSingleBeanList;
    }

    public void setSingleBeanList(List<ArticleSingleBean> singleBeanList) {
        mSingleBeanList = singleBeanList;
    }
}
