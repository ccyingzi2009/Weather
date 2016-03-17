package netease.com.weather.util.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.lang.ref.WeakReference;


/**
 * Created by ls on 15-12-15 上午10:26.
 */
public abstract class BaseRequest<T> extends Request<T> {

    private WeakReference<Response.Listener<T>> mListenerRef;


    public BaseRequest(String url, Response.Listener listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    public BaseRequest(int method, String url, Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListenerRef = new WeakReference<Response.Listener<T>>(listener);
        //volley内部根据url缓存，post情况下url一致但参数不一致，故设置post情况下不使用cache
        if (method == Method.POST) {
            setShouldCache(false);
        }
    }

    @Override
    protected void deliverResponse(T response) {
        if (mListenerRef != null && mListenerRef.get() != null) {
            mListenerRef.get().onResponse(response);
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
}
