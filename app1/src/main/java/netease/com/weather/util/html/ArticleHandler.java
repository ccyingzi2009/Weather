package netease.com.weather.util.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import netease.com.weather.data.model.ArticleBean;
import netease.com.weather.util.string.StringUtils;

/**
 * Created by user on 16-3-17.
 */
public class ArticleHandler implements HtmlHandler<List<ArticleBean>> {

    @Override
    public List<ArticleBean> process(String html) {
        //利用正则表达式解析数据



        Element element = Jsoup.parse(html);
        List<ArticleBean> arrayList = new ArrayList<>();
        Element e = element.select("ul").first();
        Elements lis = e.select("li");
        for (Element li : lis) {
            if (li.select("div") != null && li.select("div").size() >= 2) {
                Element content = li.select("div").get(3);
                if (content != null) {
                    ArticleBean bean = new ArticleBean();
                    Object[] object = StringUtils.parseMobileContent(content.html());
                    bean.setContent((String) object[0]);
                    arrayList.add(bean);
                }
            }
        }
        return arrayList;
    }

    @Override
    public void save() {

    }
}
