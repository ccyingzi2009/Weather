package netease.com.weather.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import netease.com.weather.R;
import netease.com.weather.ui.common.AdapterHandler;
import netease.com.weather.ui.common.ListAdapter;

/**
 * Created by ls on 16-8-3.
 */
public abstract class BaseLoadListFragment<T> extends BaseLoadFragment<List<T>> implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private ListAdapter<T> mAdapter;

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
        recycleView.setLayoutManager(createLayoutManager());
        loadNet();
    }


    protected abstract ListAdapter<T> createAdapter();

    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public void loadNet() {
        onPullDownToRefresh();
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
                recycleView.setAdapter((PageAdapter) mAdapter);
            }
            mAdapter.notifyDataSetChanged();

        } else if (mode == RefreshMode.more) {
            AdapterHandler.notifyDataSetChanged(mAdapter, response, false);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        onPullDownToRefresh();
    }
}
