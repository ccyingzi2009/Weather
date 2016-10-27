package netease.com.weather.ui.biz.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;


import com.google.gson.reflect.TypeToken;

import java.util.List;

import netease.com.weather.data.model.ArticleBean;
import netease.com.weather.data.model.ArticleSingleBean;
import netease.com.weather.ui.base.BaseLoadListFragment;
import netease.com.weather.ui.base.PageAdapter;
import netease.com.weather.ui.base.constants.Constants;
import netease.com.weather.util.JsonUtils;
import netease.com.weather.util.PrefHelper;
import netease.com.weather.util.html.ArticleHandler;
import netease.com.weather.util.request.BaseRequest;
import netease.com.weather.util.request.HtmlRequest;

/**
 * Created by liu_shuai on 16/8/22.
 */
public class ArticleNewFragment extends BaseLoadListFragment<ArticleSingleBean> {

    public final static String ARTICLE_URL = "article_url";
    private String mArticleUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mArticleUrl = args.getString(ARTICLE_URL);
            //Toast.makeText(getContext(), "增量", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected BaseRequest<List<ArticleSingleBean>> onCreateNet(RefreshMode refreshMode) {
        if (refreshMode == RefreshMode.refresh) {
            String url = String.format(Constants.M_ARTICLE_URL, mArticleUrl);
            return new HtmlRequest<>(url, new ArticleHandler(), "utf-8", true);
        } else if (refreshMode == RefreshMode.more) {
            String url = String.format(Constants.M_ARTICLE_URL, mArticleUrl) + "?p=" + (mPage + 1);
            return new HtmlRequest<>(url, new ArticleHandler(), "utf-8", false);
        }

        return super.onCreateNet(refreshMode);
    }

    @Override
    protected PageAdapter<ArticleSingleBean> createAdapter() {
        return new ArticleNewAdapter((ArticleActivity) getActivity(), this);
    }

    @Override
    protected List<ArticleSingleBean> getLocalData() {
        super.getLocalData();

        List<ArticleSingleBean> data = null;
        String url = String.format(Constants.M_ARTICLE_URL, mArticleUrl);
        String articleList = PrefHelper.getString(Constants.PREF_ARCICLE, url, "");
        if (!TextUtils.isEmpty(articleList)) {
            ArticleBean bean = JsonUtils.fromJson(articleList, new TypeToken<ArticleBean>() {
            });
            if (bean != null) {
                data = bean.getSingleBeanList();
            }
        }
        return data;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //回复成功调用
    public void replySuccess() {
        loadNet();
    }
}
