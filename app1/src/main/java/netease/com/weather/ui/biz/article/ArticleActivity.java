package netease.com.weather.ui.biz.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import netease.com.weather.R;
import netease.com.weather.data.DataManager.DataManager;
import netease.com.weather.data.api.BYService;
import netease.com.weather.data.model.Article;
import netease.com.weather.ui.base.BaseActivity;
import netease.com.weather.ui.common.AdapterHandler;
import netease.com.weather.ui.view.InfiniteScrollListener;

/**
 * Created by user on 16-4-21.
 */
public class ArticleActivity extends BaseActivity {

    @Bind(R.id.recycle_view)
    RecyclerView mRecycleView;

    private DataManager mDataManager;
    private ArticleAdapter mAdapter;

    private String mName;
    private String mId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Bundle args = getIntent().getExtras();

        if (args != null) {
            mName = args.getString("name");
            mId = args.getString("id");
        }
        ButterKnife.bind(this);

        mDataManager = new DataManager(mName, mId, BYService.PER_PAGE_DEFAULT) {

            @Override
            public void onDataLoaded(Article data, boolean isRefresh) {
                List<Article.ArticleEntity> articleEntities = data.getArticle();
                AdapterHandler.notifyDataSetChanged(mAdapter, articleEntities, isRefresh);
            }
        };

        mDataManager.registerCallback(mAdapter);
        mAdapter = new ArticleAdapter();
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addOnScrollListener(new InfiniteScrollListener((LinearLayoutManager) mRecycleView.getLayoutManager(), mDataManager
        ) {
            @Override
            public void onLoadMore() {
                mDataManager.loadData();
            }
        });

        mDataManager.loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
