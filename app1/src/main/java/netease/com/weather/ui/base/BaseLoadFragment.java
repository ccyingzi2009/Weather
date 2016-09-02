package netease.com.weather.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;

import butterknife.BindView;
import butterknife.OnClick;
import netease.com.weather.R;
import netease.com.weather.util.NetUtils;
import netease.com.weather.util.request.BaseRequest;
import netease.com.weather.util.request.VolleyUtils;

/**
 * Created by user on 16-8-3.
 */
public abstract class BaseLoadFragment<T> extends BaseFragment {

    @Nullable @BindView(R.id.loading_view)
    View loadingView;
    @Nullable @BindView(R.id.layoutLoadFailed)
    View loadingFailed;
    @Nullable @BindView(R.id.layoutContent)
    View layoutContent;

    private boolean mIsEmpty = true;

    public enum RefreshMode {
        normal, //普通刷新
        refresh, //下拉刷新
        more, // 加载更多
    }

    public enum TaskState {
        prepare,
        success,
        failed,
    }

    @Override
    protected int getContentViewId() {
        return R.layout.comm_load_fragment;
    }

    @Nullable @OnClick(R.id.layoutLoadFailed)
    void onFiledClick() {
        loadNet();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLocalData();
    }

    //同步方法
    protected T getLocalData() {
        return null;
    }

    protected void initLocalData() {
        T data = getLocalData();
        if (data != null) {
            onNetResponse(RefreshMode.refresh, data);
        }
    }

    public void loadNet() {
        onTaskStateChange(TaskState.prepare);
        createRequest(RefreshMode.normal);
    }

    protected void createRequest(final RefreshMode refreshMode) {
        BaseRequest<T> request = onCreateNet(refreshMode);
        if (request != null) {
            request.setResponseListener(new BaseRequest.IResponseListener<T>() {
                @Override
                public void onResponse(T response) {
                    onNetResponse(refreshMode, response);
                }

                @Override
                public void onError(VolleyError error) {
                    //Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    onErrorResponse(refreshMode, error);
                }
            });

            VolleyUtils.addRequest(request, this);
        }
        if (!NetUtils.checkNetwork()) {
            Toast.makeText(getContext(), "请检测网络", Toast.LENGTH_SHORT).show();
        }

    }

    protected BaseRequest<T> onCreateNet(RefreshMode refreshMode) {
        return null;
    }

    public void onErrorResponse(RefreshMode mode, VolleyError error) {
        onTaskStateChange(TaskState.failed);
    }

    public void onNetResponse(RefreshMode mode, T response) {
        if (response != null) {
            onTaskStateChange(TaskState.success);
            setIsEmpty(false);
        }
    }

    public void setIsEmpty(boolean isEmpty) {
        this.mIsEmpty = isEmpty;
    }

    protected boolean isContentEmpty() {
        return mIsEmpty;
    }

    protected void onTaskStateChange(TaskState state) {
        if (state == TaskState.prepare) {
            if (isContentEmpty()) {
                setViewVisible(layoutContent, View.GONE);
                setViewVisible(loadingView, View.VISIBLE);
            } else {
                setViewVisible(layoutContent, View.VISIBLE);
                setViewVisible(loadingView, View.GONE);
            }
            setViewVisible(loadingFailed, View.GONE);
        } else if (state == TaskState.success) {
            setViewVisible(layoutContent, View.VISIBLE);
            setViewVisible(loadingView, View.GONE);
            setViewVisible(loadingFailed, View.GONE);
        } else if (state == TaskState.failed) {
            if (isContentEmpty()) {
                setViewVisible(layoutContent, View.GONE);
                setViewVisible(loadingView, View.GONE);
                setViewVisible(loadingFailed, View.VISIBLE);
            } else {
                setViewVisible(layoutContent, View.VISIBLE);
                setViewVisible(loadingView, View.GONE);
                setViewVisible(loadingFailed, View.GONE);
            }
        }
    }

    protected void setViewVisible(View v, int visible){
        if (v != null && v.getVisibility() != visible) {
            v.setVisibility(visible);
        }
    }

}
