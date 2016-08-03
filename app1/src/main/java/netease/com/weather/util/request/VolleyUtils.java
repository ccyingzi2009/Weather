package netease.com.weather.util.request;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import netease.com.weather.BaseApplication;


/**
 * Volley管理类
 */
public class VolleyUtils {
    private static VolleyUtils mInstance;
    private RequestQueue mRequestQueue;
    //private ImageLoader mImageLoader;
    public static final String ID_LOAD_NET_TAG = "load_net";
    public static final String ID_LOAD_MORE_TAG = "load_more";

    private VolleyUtils() {
        // no instances
        init(BaseApplication.getInstance());
    }

    private void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context, null);
    }

    private static VolleyUtils getSingleton() {
        if (null == mInstance) {
            synchronized (VolleyUtils.class) {
                if (mInstance == null) {
                    mInstance = new VolleyUtils();
                }
            }
        }
        return mInstance;
    }

    public static RequestQueue getRequestQueue() {
        VolleyUtils manager = getSingleton();
        return manager.mRequestQueue;
    }

    public static void addRequest(Request<?> request, Object tag) {
        RequestQueue requestQueue = getRequestQueue();
        if (tag != null && request != null) {
            request.setTag(tag);
        }
        if (requestQueue == null) {
            return;
        }
        if (request == null) {
            return;
        }
        requestQueue.add(request);
    }

    public static void cancelAll(Context context, Object tag) {
        RequestQueue requestQueue = getRequestQueue();
        requestQueue.cancelAll(tag);
    }
}
