package netease.com.weather.util.html;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import netease.com.weather.model.MainPage;
import netease.com.weather.model.MainSlider;

/**
 * Created by user on 16-3-17.
 */
public class MainPageHandler implements HtmlHandler<List<MainSlider>> {

    @Override
    public List<MainSlider> process(Element element) {
        /*String title = element.getElementsByTag("title").toString();
        Elements elements = element.select("ul.mangeListBox").select("li");

        List<String> coverUrls = new ArrayList<>();
        for (Element e : elements) {
            String coverUrl = e.select("img").attr("src");
            coverUrls.add(coverUrl);
        }
        return new MainPage(coverUrls);*/
        Elements elements = element.select("table.hotTable").select("tbody");
        List<MainSlider> sliders = new ArrayList<>();
        for (Element e : elements) {
            String href = e.select("td.HotTitle").select("a").get(0).attr("href");
            String data = e.select("td.HotTitle").select("a").get(0).text();
            MainSlider slider = new MainSlider(href, data, "");
            sliders.add(slider);
        }
        return sliders;
    }

    @Override
    public void save() {

    }
}
