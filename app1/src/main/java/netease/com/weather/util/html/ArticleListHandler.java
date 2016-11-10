package netease.com.weather.util.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
        return parseHtmlByJsoup(html);
    }

    @Override
    public void save(String key) {
        super.save(key);
    }


    /**
     *
     * @param html
     * @return
     * 解析的数据类型如下
     * <li>
        <div>
        <a href="/article/BIT/251211">Re: 岗位故事征稿</a>(1)</div>
        <div>2016-10-23&nbsp;
        <a href="/user/query/原帖已删除">原帖已删除</a>|2016-10-23&nbsp;
        <a href="/user/query/zolans">zolans</a></div>
        </li>
     *
     */
    private List<MainSlider> parseHtmlByJsoup(String html) {
        Element element = Jsoup.parse(html);
        List<MainSlider> arrayList = new ArrayList<>();
        Element e = element.getElementById("m_main");
        Elements listSecElements = e.getElementsByClass("list sec");
        if (listSecElements != null) {
            Element listSec = listSecElements.first();
            if (listSec != null) {
                Elements liElements = listSec.select("li");
                if (liElements != null) {
                    for (Element liElement : liElements) {
                        Elements divs = liElement.select("div");
                        if (divs != null && divs.size() >= 2) {
                            
                        }
                    }
                }
            }
        }
        return arrayList;
    }
}
