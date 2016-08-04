package netease.com.weather.util.html;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import netease.com.weather.data.model.MainSlider;

/**
 * Created by user on 16-3-17.
 */
public class MainPageHandler implements HtmlHandler<List<MainSlider>> {

    @Override
    public List<MainSlider> process(Element element) {
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
        return mainSliders;
    }

    @Override
    public void save() {

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
            mainSliders.add(slider);
        }
    }
}
