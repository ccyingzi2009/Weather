package netease.com.weather.util.html;

/**
 * Created by user on 16-3-17.
 */
public interface HtmlHandler<T> {
    T process(String html);
    void save(String key);
}
