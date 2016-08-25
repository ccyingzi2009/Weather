package netease.com.weather.util.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                ArticleBean bean = null;
                //获取content
                Element content = li.select("div").get(3);
                if (content != null) {
                    bean = new ArticleBean();
                    Object[] object = StringUtils.parseMobileContent(content.html());
                    bean.setContent((String) object[0]);
                    bean.setImgs((ArrayList<String>) object[2]);
                    arrayList.add(bean);
                }else {
                    continue;
                }
                //获取其它信息，通过正则表达式
                Pattern urlPattern = Pattern.compile("<a href=\"/user/query/([^<>]+)\">");
                Matcher uMatcher = urlPattern.matcher(li.html());
                if (uMatcher.find()) {
                    bean.setUser(uMatcher.group(1));
                }

            }
        }
        return arrayList;
    }

    @Override
    public void save() {

    }
}
