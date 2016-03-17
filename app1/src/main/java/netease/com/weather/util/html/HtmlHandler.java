package netease.com.weather.util.html;

import org.jsoup.nodes.Element;

/**
 * Created by user on 16-3-17.
 */
public interface HtmlHandler<T> {
    T process(Element element);
    void save();
}
