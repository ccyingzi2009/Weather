package netease.com.weather.ui.biz.article;

import java.util.List;

import netease.com.weather.data.model.ArticleBean;
import netease.com.weather.ui.base.BaseLoadListFragment;
import netease.com.weather.ui.base.IBaseAdapter;
import netease.com.weather.ui.common.ListAdapter;
import netease.com.weather.util.html.ArticleHandler;
import netease.com.weather.util.request.BaseRequest;
import netease.com.weather.util.request.HtmlRequest;

/**
 * Created by liu_shuai on 16/8/22.
 */
public class ArticleNewFragment extends BaseLoadListFragment<ArticleBean> {

    @Override
    protected BaseRequest<List<ArticleBean>> onCreateNet(RefreshMode refreshMode) {
        if (refreshMode == RefreshMode.refresh) {
            String url = "http://m.newsmth.net/article/Mobile/1681420";
            return new HtmlRequest<>(url, new ArticleHandler(), "utf-8");
        } else {

        }

        return super.onCreateNet(refreshMode);
    }

    @Override
    protected ListAdapter<ArticleBean> createAdapter() {
        return new ArticleNewAdapter();
    }
}
