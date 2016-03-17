package netease.com.weather.util.request;

import android.app.ActivityManager;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


/**
 * Volley管理类
 */
public class VolleyUtils {
    private static VolleyUtils mRequestManager;
    private RequestQueue mRequestQueue;
    //private ImageLoader mImageLoader;
    public static final String ID_LOAD_NET_TAG = "load_net";
    public static final String ID_LOAD_MORE_TAG = "load_more";

    private VolleyUtils(Context context) {
        // no instances
        context = context.getApplicationContext();
        init(context);
    }

    private void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context, null);

        int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE))
                .getMemoryClass();
        // Use 1/8th of the available memory for this memory cache.
        int cacheSize = 1024 * 1024 * memClass / 8;
        //mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(cacheSize));

    }

/*    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }*/

    private static synchronized VolleyUtils getRequestManager(Context context) {
        VolleyUtils requestManager = mRequestManager;
        if (requestManager == null) {
            requestManager = new VolleyUtils(context);
            mRequestManager = requestManager;
        }
        return requestManager;
    }

    public static RequestQueue getRequestQueue(Context context) {
        VolleyUtils manager = getRequestManager(context);
        return manager.mRequestQueue;
    }

    public static void addRequest(Context context, Request<?> request, Object tag) {
        RequestQueue requestQueue = getRequestQueue(context);
        if (tag != null && request != null) {
            request.setTag(tag);
        }
        if (requestQueue == null) {
            return;
        }
        if (request == null){
            return;
        }
        requestQueue.add(request);
    }

    public static void cancelAll(Context context, Object tag) {
        RequestQueue requestQueue = getRequestQueue(context);
        requestQueue.cancelAll(tag);
    }

   /* *//**
     * Returns instance of ImageLoader initialized with {@see FakeImageCache}
     * which effectively means that no memory caching is used. This is useful
     * for images that you know that will be show only once.
     *
     * @return
     *//*
    public static ImageLoader getImageLoader(Context context) {
        ImageLoader imageLoader = getRequestManager(context).mImageLoader;
        if (imageLoader != null) {
            return imageLoader;
        } else {
            throw new IllegalStateException("ImageLoader not initialized");
        }
    }*/


}
