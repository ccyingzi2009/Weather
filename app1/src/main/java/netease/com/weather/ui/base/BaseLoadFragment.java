package netease.com.weather.ui.base;

import android.os.Bundle;
import android.view.View;

import com.android.volley.VolleyError;

import netease.com.weather.util.request.BaseRequest;
import netease.com.weather.util.request.VolleyUtils;

/**
 * Created by user on 16-8-3.
 */
public abstract class BaseLoadFragment<T> extends BaseFragment {

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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLocalData();
    }

    //同步方法
    protected T getLocalData() {
        T t = null;
        return t;
    }

    protected void initLocalData() {

    }

    public void loadNet() {
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

    }

    protected BaseRequest<T> onCreateNet(RefreshMode refreshMode) {
        return null;
    }

    public void onErrorResponse(RefreshMode mode, VolleyError error) {

    }

    public void onNetResponse(RefreshMode mode, T response) {

    }

    public void setIsEmpty(boolean isEmpty) {
        this.mIsEmpty = isEmpty;
    }

    protected boolean isContentEmpty(){
        return mIsEmpty;
    }

    protected void onTaskStateChange(TaskState state) {

    }
}
