package netease.com.weather.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.android.volley.VolleyError;

import java.util.List;

import butterknife.BindView;
import netease.com.weather.R;
import netease.com.weather.ui.common.AdapterHandler;
import netease.com.weather.ui.view.CustomItemDecoration;

/**
 * Created by ls on 16-8-3.
 */
public abstract class BaseLoadListFragment<T> extends BaseLoadFragment<List<T>> implements
        SwipeRefreshLayout.OnRefreshListener, PageAdapter.OnFooterViewCallback {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private PageAdapter<T> mAdapter;
    protected int mPage = -1;
    protected int mSize = 10;
    private RefreshConfig mRefreshConfig;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = createAdapter();

        mRefreshConfig = new RefreshConfig();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.comm_load_list_fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadNet();
    }

    @Override
    protected void initLayout(View v, LayoutInflater inflater, ViewGroup container) {
        super.initLayout(v, inflater, container);
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
        recycleView.addItemDecoration(new CustomItemDecoration(getContext(), CustomItemDecoration.VERTICAL_LIST));

        ((BaseActivity) getActivity()).setSupportActionBar(toolbar);
    }

    protected abstract PageAdapter<T> createAdapter();

    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public void loadNet() {
        onPullDownToRefresh();
    }

    @Override
    public void loadMore() {
        onPullUpToRefresh();
    }


    @Override
    public void onRefresh() {
        onPullDownToRefresh();
    }

    protected void onScrollStateChanged(int state) {
        if (recycleView == null) {
            return;
        }
        if (state == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && mPage != -1 && !mRefreshConfig.pageEnd) {
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
        }
    }

    //下拉刷新
    protected void onPullDownToRefresh() {
        onTaskStateChange(TaskState.prepare);
        createRequest(RefreshMode.refresh);
        swipeRefreshLayout.setRefreshing(true);
    }

    //上拉加载更多
    protected void onPullUpToRefresh() {
        createRequest(RefreshMode.more);
        mRefreshConfig.isLoadingMore = true;
    }

    @Override
    protected List<T> getLocalData() {
        return null;
    }

    @Override
    final protected void initLocalData() {
        List<T> data = getLocalData();
        if (data != null && !data.isEmpty()) {
            onNetResponse(RefreshMode.refresh, data);
        }
    }

    @Override
    final public void onNetResponse(RefreshMode mode, List<T> response) {
        super.onNetResponse(mode, response);
        if (swipeRefreshLayout == null) return;

        if (mode == RefreshMode.refresh) { //下拉刷新
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            AdapterHandler.notifyDataSetChanged(mAdapter, response);
            if (recycleView.getAdapter() == null) {
                recycleView.setAdapter(mAdapter);
            }
            mAdapter.notifyDataSetChanged();
            mPage = 1;
            mAdapter.showFooter(true);
            mRefreshConfig.pageEnd = false;
            onTaskStateChange(TaskState.success);
            setIsEmpty(response.size() == 0);
        } else if (mode == RefreshMode.more) {
            AdapterHandler.notifyDataSetChanged(mAdapter, response, false);
            mAdapter.notifyDataSetChanged();
            mPage++;
            mRefreshConfig.isLoadingMore = false;
        }
        if (response.size() < mSize) {
            mAdapter.showFooter(false);
            mRefreshConfig.pageEnd = true;
        }
    }

    @Override
    public void onErrorResponse(RefreshMode mode, VolleyError error) {
        super.onErrorResponse(mode, error);
        if (swipeRefreshLayout == null) return;

        if (mode == RefreshMode.more) {
            mRefreshConfig.isLoadingMore = false;
        }

        if (mode == RefreshMode.refresh && swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            onTaskStateChange(TaskState.failed);
        }

    }


    public static class RefreshConfig {
        public boolean pageEnd = false;
        public boolean isLoadingMore = false;
    }

    @Override
    protected void onTaskStateChange(TaskState state) {
        super.onTaskStateChange(state);

    }
}
