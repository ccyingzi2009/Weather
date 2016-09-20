package netease.com.weather.util.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by ls on 15-12-15 上午10:26.
 */
public abstract class BaseRequest<T> extends Request<T> {

    protected String mUrl;
    protected Map<String, String> mHeaders = new HashMap<>();
    public BaseRequest(String url) {
        this(url, null);
        mUrl = url;
    }

    public BaseRequest(String url, IResponseListener<T> responseListener) {
        this(Method.GET, url, responseListener);
        mUrl = url;
    }

    public BaseRequest(int method, String url, IResponseListener<T> responseListener) {
        super(method, url, null);
        //volley内部根据url缓存，post情况下url一致但参数不一致，故设置post情况下不使用cache
        if (method == Method.POST) {
            setShouldCache(false);
        }
        mListener = responseListener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        mHeaders.put("X-Requested-With", "XMLHttpRequest");
        return mHeaders;
    }

    @Override
    protected void deliverResponse(T response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }


    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError.networkResponse != null) {
            int statusCode = volleyError.networkResponse.statusCode;
            if (statusCode == 401) {

            }
        }
        return super.parseNetworkError(volleyError);
    }

    @Override
    public void deliverError(VolleyError error) {
        if (mListener != null) {
            mListener.onError(error);
        }
    }

    private IResponseListener<T> mListener;

    public void setResponseListener(IResponseListener<T> listener) {
        mListener = listener;
    }

    public interface IResponseListener<T> {
        void onResponse(T response);

        void onError(VolleyError error);
    }
}
