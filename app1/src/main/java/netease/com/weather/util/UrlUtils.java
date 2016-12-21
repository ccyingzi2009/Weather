package netease.com.weather.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import netease.com.weather.ui.biz.article.ArticleActivity;
import netease.com.weather.ui.biz.article.ArticleNewFragment;
import netease.com.weather.ui.biz.web.WebViewActivity;
import netease.com.weather.ui.biz.web.WebViewFragment;

/**
 * Created by liu_shuai on 2016/12/21.
 */

public class UrlUtils {


    public static void startActivity(Context context, String url) {
        boolean match = false;
        if (url.contains("http://m.newsmth.net/article") || //
                url.contains("http://www.newsmth.net/nForum")) {
            if (url.indexOf("article/") > 0) {
                String articleUrl = url.substring(url.indexOf("article/"));
                if (!TextUtils.isEmpty(articleUrl)) {
                    Intent i = new Intent(context, ArticleActivity.class);
                    i.putExtra(ArticleNewFragment.ARTICLE_URL, articleUrl);
                    context.startActivity(i);
                    match = true;
                }
            }
        } else {

        }

        if (!match) {
            Intent i = new Intent(context, WebViewActivity.class);
            i.putExtra(WebViewFragment.PARAM_WEB_URL, url);
            context.startActivity(i);
        }
    }
}
