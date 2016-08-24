package netease.com.weather.ui.biz.article;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.List;

import netease.com.weather.data.model.ArticleBean;
import netease.com.weather.ui.base.BaseLoadListFragment;
import netease.com.weather.ui.base.PageAdapter;
import netease.com.weather.ui.base.constants.Constants;
import netease.com.weather.util.html.ArticleHandler;
import netease.com.weather.util.request.BaseRequest;
import netease.com.weather.util.request.HtmlRequest;

/**
 * Created by liu_shuai on 16/8/22.
 */
public class ArticleNewFragment extends BaseLoadListFragment<ArticleBean> {

    public final static String ARTICLE_URL = "article_url";

    private String mArticleUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mArticleUrl = args.getString(ARTICLE_URL);
        }
    }

    @Override
    protected BaseRequest<List<ArticleBean>> onCreateNet(RefreshMode refreshMode) {
        if (refreshMode == RefreshMode.refresh) {
            String url = String.format(Constants.M_ARTICLE_URL, mArticleUrl);
            return new HtmlRequest<>(url, new ArticleHandler(), "utf-8");
        } else if (refreshMode == RefreshMode.more) {
            String url = String.format(Constants.M_ARTICLE_URL, mArticleUrl) + "?p=" + (mPage + 1);
            return new HtmlRequest<>(url, new ArticleHandler(), "utf-8");
        }

        return super.onCreateNet(refreshMode);
    }

    @Override
    protected PageAdapter<ArticleBean> createAdapter() {
        return new ArticleNewAdapter(this);
    }
}
