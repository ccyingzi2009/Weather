package netease.com.weather.ui.base;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.VolleyError;

import netease.com.weather.util.request.BaseRequest;
import netease.com.weather.util.request.VolleyUtils;

/**
 * Created by user on 16-8-3.
 */
public abstract class BaseLoadFragment<T> extends BaseFragment {

    public enum RefreshMode {
        normal, //普通刷新
        refresh, //下拉刷新
        more, // 加载更多
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                    Log.v("volley", error.getMessage());
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
}
