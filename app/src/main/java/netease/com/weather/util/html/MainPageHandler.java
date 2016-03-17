package netease.com.weather.util.html;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import netease.com.weather.model.MainPage;

/**
 * Created by user on 16-3-17.
 */
public class MainPageHandler implements HtmlHandler<MainPage> {

    @Override
    public MainPage process(Element element) {
        String title = element.getElementsByTag("title").toString();
        Elements elements = element.select("ul.mangeListBox").select("li");

        List<String> coverUrls = new ArrayList<>();
        for (Element e : elements) {
            String coverUrl = e.select("img").attr("src");
            coverUrls.add(coverUrl);
        }
        return new MainPage(coverUrls);
    }

    @Override
    public void save() {

    }
}
