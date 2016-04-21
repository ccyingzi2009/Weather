package netease.com.weather.ui.biz.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import netease.com.weather.R;
import netease.com.weather.data.DataManager.DataManager;
import netease.com.weather.data.model.Article;
import netease.com.weather.ui.base.BaseActivity;
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
    private int page = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Bundle args = getIntent().getExtras();
        if (args != null) {

        }
        ButterKnife.bind(this);

        mDataManager = new DataManager() {

            @Override
            public void onDataLoaded(Article data) {

            }
        };

        mAdapter = new ArticleAdapter();
        mRecycleView.addOnScrollListener(new InfiniteScrollListener((LinearLayoutManager) mRecycleView.getLayoutManager(), mDataManager
        ) {
            @Override
            public void onLoadMore() {

            }
        });

        mDataManager.loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
