package netease.com.weather.util.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import netease.com.weather.data.model.ArticleBean;
import netease.com.weather.data.model.ArticleSingleBean;
import netease.com.weather.ui.base.constants.Constants;
import netease.com.weather.util.JsonUtils;
import netease.com.weather.util.PrefHelper;
import netease.com.weather.util.StringUtils;

/**
 * Created by user on 16-3-17.
 */
public class ArticleHandler extends BaseHandler<List<ArticleSingleBean>> {

    @Override
    public List<ArticleSingleBean> process(String html) {
        //利用正则表达式解析数据
        Element element = Jsoup.parse(html);
        List<ArticleSingleBean> arrayList = new ArrayList<>();
        Element e = element.select("ul").first();
        Elements lis = e.select("li");
        for (Element li : lis) {
            if (li.select("div") != null && li.select("div").size() >= 2) {
                ArticleSingleBean bean = null;
                //获取content
                Element content = li.select("div").get(3);
                if (content != null) {
                    bean = new ArticleSingleBean();
                    Object[] object = StringUtils.parseMobileContent(content.html());
                    bean.setContent((String) object[0]);//设置内容
                    bean.setQuote((String) object[1]); //设置引用
                    bean.setImgs((ArrayList<String>) object[2]); //设置图片数据
                    arrayList.add(bean);
                } else {
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

        setData(arrayList);
        return arrayList;
    }

    //<a class=\"plant\">(\d*\/\d*)< 获取分页数据

    @Override
    public void save(String url) {
        List<ArticleSingleBean> arrayList = getData();
        ArticleBean articleBean = new ArticleBean();
        articleBean.setSingleBeanList(arrayList);
        String result = JsonUtils.toJson(articleBean);
        PrefHelper.putString(Constants.PREF_ARCICLE, url, result);
    }
}
