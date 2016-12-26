package netease.com.weather.ui.biz.article;

import android.text.TextUtils;

/**
 * Created by user on 16-10-27.
 */

public class ArticleModel {
    public final static String ARTICLE_BOARDID = "boardId";
    public final static String ARTICLE_TITLE = "title";
    public final static String ARTICLE_ID = "articleId";

    public static String[] getPage(String page) {
        if (!TextUtils.isEmpty(page)) {
            String pages[] = page.split("/");
            if (pages.length == 2) {
                return pages;
            }
        }
        return null;
    }
}
