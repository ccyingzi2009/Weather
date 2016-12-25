package netease.com.weather.ui.biz.article;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import netease.com.weather.data.model.ArticleBean;
import netease.com.weather.data.model.ArticleSingleBean;
import netease.com.weather.ui.base.BaseLoadListFragment;
import netease.com.weather.ui.base.PageAdapter;
import netease.com.weather.ui.base.constants.Constants;
import netease.com.weather.ui.common.CommentReply;
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

    private int mLastDy = 0;
    private Handler mHandler = new Handler();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mArticleUrl = args.getString(ARTICLE_URL);
            //Toast.makeText(getContext(), "增量", Toast.LENGTH_SHORT).show();
        }

        setHasOptionsMenu(true);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRefreshEnable(false); //禁用下拉刷新
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


    //回复成功调用
    public void replySuccess() {
        loadNet();
    }

    @Override
    protected void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        System.out.println(dy);
        if ((mLastDy > 0 && dy > 0) || (mLastDy < 0 && dy < 0)) {
            return;
        }else {
            mLastDy = dy;
            if (getActivity() instanceof ArticleActivity) {
                ArticleActivity articleActivity = (ArticleActivity) getActivity();
                final CommentReply commentReply = articleActivity.getCommentReply();
                if (commentReply != null) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mLastDy > 0) {
                                //隐藏
                                commentReply.setEditTextShow(false);
                            } else {
                                //显示
                                commentReply.setEditTextShow(true);
                            }
                        }
                    }, 200);

                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
