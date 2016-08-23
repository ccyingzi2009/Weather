package netease.com.weather.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;

import java.util.List;

import butterknife.Bind;
import netease.com.weather.R;
import netease.com.weather.ui.common.AdapterHandler;

/**
 * Created by ls on 16-8-3.
 */
public abstract class BaseLoadListFragment<T> extends BaseLoadFragment<List<T>> implements
        SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private PageAdapter<T> mAdapter;
    protected int mPage = -1;

    private RefreshMode mMode = RefreshMode.refresh;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = createAdapter();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.comm_load_list_fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(this);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                BaseLoadListFragment.this.onScrollStateChanged(newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        recycleView.setLayoutManager(createLayoutManager());
        loadNet();
    }


    protected abstract PageAdapter<T> createAdapter();

    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public void loadNet() {
        onPullDownToRefresh();
    }

    protected void onScrollStateChanged(int state) {
        if (state == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && mPage != -1) {
            int count = recycleView.getChildCount();
            RecyclerView.LayoutManager layoutManager = recycleView.getLayoutManager();
            int totalCount = 0;
            int firstVisibleItem = 0;
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager manager = (LinearLayoutManager) recycleView.getLayoutManager();
                totalCount = manager.getItemCount();
                firstVisibleItem = manager.findFirstVisibleItemPosition();
            }

            if (totalCount == count + firstVisibleItem) {
                onPullUpToRefresh();
            }
            Log.d("recycleView====", count + "  " + totalCount + "  " + firstVisibleItem);
            //if (count > 0 && recycleView.getChildAt(count - 1) == )
        }
    }

    //下拉刷新
    protected void onPullDownToRefresh() {
        createRequest(RefreshMode.refresh);
        swipeRefreshLayout.setRefreshing(true);
    }

    //上拉加载更多
    protected void onPullUpToRefresh() {
        createRequest(RefreshMode.more);
    }

    @Override
    public void onNetResponse(RefreshMode mode, List<T> response) {
        super.onNetResponse(mode, response);
        if (mode == RefreshMode.refresh && swipeRefreshLayout.isRefreshing()) { //下拉刷新
            swipeRefreshLayout.setRefreshing(false);
            AdapterHandler.notifyDataSetChanged(mAdapter, response);
            if (recycleView.getAdapter() == null) {
                recycleView.setAdapter(mAdapter);
            }
            mAdapter.notifyDataSetChanged();
            mPage = 1;
            mAdapter.showFooter(true);
            mMode = RefreshMode.refresh;
        } else if (mode == RefreshMode.more) {
            AdapterHandler.notifyDataSetChanged(mAdapter, response, false);
            mAdapter.notifyDataSetChanged();
            mPage++;
            if (response.size() == 0) {
                mAdapter.showFooter(false);
            }
            mMode = RefreshMode.more;

        }
    }

    @Override
    public void onRefresh() {
        onPullDownToRefresh();
    }
}
