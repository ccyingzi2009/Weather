package netease.com.weather.util.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

import netease.com.weather.data.model.ArticleSingleBean;
import netease.com.weather.data.model.MainSlider;

/**
 * Created by liu_shuai on 2016/11/8.
 */

public class ArticleListHandler extends BaseHandler<List<MainSlider>> {

    @Override
    public List<MainSlider> process(String html) {
        Element element = Jsoup.parse(html);
        List<ArticleSingleBean> arrayList = new ArrayList<>();
        Element e = element.getElementById("m_main");

        return super.process(html);
    }

    @Override
    public void save(String key) {
        super.save(key);
    }
}
