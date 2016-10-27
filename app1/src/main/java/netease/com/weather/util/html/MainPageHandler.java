package netease.com.weather.util.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import netease.com.weather.data.model.MainBean;
import netease.com.weather.data.model.MainSlider;
import netease.com.weather.util.JsonUtils;
import netease.com.weather.util.PrefHelper;

/**
 * Created by user on 16-3-17.
 */
public class MainPageHandler extends BaseHandler<MainBean> {

    @Override
    public MainBean process(String html) {
        Element element = Jsoup.parse(html);
        List<MainSlider> mainSliders = new ArrayList<>();
        //解析十大
        Element elementNode = element.getElementById("top10");
        parseData(elementNode, mainSliders, "top10");
        //解析其它版块
        Elements w_sections = element.getElementsByClass("w_section");
        for (Element w_section : w_sections) {
            Element section_topics = w_section.getElementsByClass("topics").first();
            if (section_topics != null) {
                String sectionName = w_section.select("h3").first().select("a").text();
                parseData(w_section.getElementsByClass("topics").first(), mainSliders, sectionName);
            }
        }

        MainBean bean = new MainBean();
        bean.setSliders(mainSliders);
        return bean;
    }

    @Override
    public void save(String key) {
        MainBean bean = getData();
        if (bean != null) {
            String manStr = JsonUtils.toJson(bean);
            PrefHelper.putString(key, manStr);
        }
    }

    private void parseData(Element elementNode, List<MainSlider> mainSliders, String senctionName) {
        Elements elements = elementNode.select("ul").select("li");
        for (Element e : elements) {
            //title
            String title = e.select("a").get(1).attr("title");
            String articleUrl = e.select("a").get(1).attr("href");

            String board = e.select("a").get(0).attr("title");
            String board_url = e.select("a").get(0).attr("href");
            MainSlider slider = new MainSlider(title, articleUrl, board, board_url);
            slider.setSection(senctionName);
            String articleId = articleUrl.substring(articleUrl.lastIndexOf("/") + 1, articleUrl.length());
            slider.setArticleId(articleId);
            String boardId = board_url.substring(board_url.lastIndexOf("/") + 1, board_url.length());
            slider.setBoardId(boardId);
            mainSliders.add(slider);
        }
    }
}
