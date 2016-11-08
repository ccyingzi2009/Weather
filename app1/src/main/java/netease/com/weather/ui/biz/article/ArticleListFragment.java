package netease.com.weather.ui.biz.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.List;

import netease.com.weather.data.model.MainSlider;
import netease.com.weather.ui.base.BaseLoadFragment;
import netease.com.weather.ui.base.BaseLoadListFragment;
import netease.com.weather.ui.base.PageAdapter;
import netease.com.weather.ui.base.constants.Constants;
import netease.com.weather.util.html.ArticleHandler;
import netease.com.weather.util.html.ArticleListHandler;
import netease.com.weather.util.request.BaseRequest;
import netease.com.weather.util.request.HtmlRequest;

/**
 * Created by user on 16-11-8.
 */

public class ArticleListFragment extends BaseLoadListFragment<MainSlider> {
    public final static String PARAM_BOARD_ID = "param_board_id";
    private String mBoardId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            if (TextUtils.isEmpty(args.getString(PARAM_BOARD_ID))) {
                mBoardId = args.getString(PARAM_BOARD_ID);
            }
        }
    }

    @Override
    protected PageAdapter<MainSlider> createAdapter() {
        return null;
    }

    @Override
    protected BaseRequest<List<MainSlider>> onCreateNet(RefreshMode refreshMode) {
        String url = String.format(Constants.URL_BOARD_ARTICLE, mBoardId);
        return new HtmlRequest<>(url, new ArticleListHandler(), "utf-8", false);
    }

    @Override
    protected void createRequest(RefreshMode refreshMode) {
        super.createRequest(refreshMode);


    }

}
