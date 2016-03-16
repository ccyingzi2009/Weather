package netease.com.weather.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

/**
 * Created by user on 16-3-16.
 */
@Root
public class MainPage {
    @Path("html/head")
    @Element
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
