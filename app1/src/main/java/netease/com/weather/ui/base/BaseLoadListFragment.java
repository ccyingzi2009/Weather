package netease.com.weather.ui.base;

import android.os.Bundle;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.List;

import netease.com.weather.util.request.BaseRequest;
import netease.com.weather.util.request.VolleyUtils;

/**
 * Created by user on 16-8-3.
 */
public class BaseLoadListFragment<T> extends BaseLoadFragment<List<T>> implements Response.Listener<List<T>>, Response.ErrorListener{

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void loadNet() {
        VolleyUtils.addRequest(onCreateNet(),this);
    }

    protected BaseRequest<List<T>> onCreateNet() {
        return null;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(List<T> response) {

    }
}
