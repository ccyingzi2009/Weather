package netease.com.weather.ui.base.constants;

/**
 * Created by user on 16-8-3.
 */
public class Constants {

    public final static String BASE_URL = "http://www.newsmth.net/nForum/";
    public final static String M_BASE_URL = "http://m.newsmth.net/";
    public final static String BASE_TEST_URL = "http://10.234.121.144/";

    public final static String MAIN_URL = BASE_URL + "mainpage?ajax";

    //文章页
    public final static String M_ARTICLE_URL = M_BASE_URL + "%s";
    //login
    public final static String URL_LOGIN = M_BASE_URL + "user/login";
    //update
    public final static String URL_UPDATE = BASE_TEST_URL + "notice.json";
    //userQuery
    public final static String URL_USER_QUERY = BASE_URL + "user/query/%s.json";
    //reply
    public final static String URL_REPLY = BASE_URL + "article/%s/ajax_post.json";

    //board
    public final static String URL_BOARD = BASE_URL + "slist.json?uid=%s&root=%s";
    //board_article
    public final static String URL_BOARD_ARTICLE = M_BASE_URL + "board/%s";



    //以下为shareP
    //PreConstant
    public final static String PREF_ARCICLE = "pref_arcicle";
    public final static String PREF_INPUT_HEIGHT = "pref_input_height";

}
