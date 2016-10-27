package netease.com.weather.util.request;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import netease.com.weather.ui.biz.pc.AccountModel;
import netease.com.weather.util.JsonUtils;
import netease.com.weather.util.PrefHelper;

/**
 * Created by user on 16-9-2.
 */
public class JsonRequest<T> extends BaseRequest<T> {

    private TypeToken<T> mTypeToken;

    public JsonRequest(String url, TypeToken<T> typeToken, IResponseListener<T> responseListener) {
        super(url, responseListener);
        mTypeToken = typeToken;
    }

    public JsonRequest(int method, String url, TypeToken<T> typeToken, IResponseListener<T> responseListener) {
        super(method, url, responseListener);
        mTypeToken = typeToken;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return super.getParams();
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        String cookie = PrefHelper.getString(AccountModel.PREF_COOKIE, "");
        if (!TextUtils.isEmpty(cookie)) {
            mHeaders.put("Cookie", cookie);
        }
        return super.getHeaders();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonStr = new String(response.data, "utf-8");
            if (!TextUtils.isEmpty(jsonStr)) {
                T o = JsonUtils.fromJson(jsonStr, mTypeToken);
                if (o != null) {
                    return Response.success(o, HttpHeaderParser.parseCacheHeaders(response));
                } else {
                    return Response.error(new VolleyError("data is empty"));
                }
            } else {
                return Response.error(new VolleyError("Result ret error"));
            }
        } catch (Exception e) {
            return Response.error(new VolleyError(e));
        }
    }
}
