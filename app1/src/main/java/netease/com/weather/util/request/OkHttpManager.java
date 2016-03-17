package netease.com.weather.util.request;

import android.os.Handler;
import android.os.Looper;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by user on 16-3-17.
 */
public class OkHttpManager {

    private static OkHttpManager mOkHttpManager;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;

    public OkHttpManager() {
        mOkHttpClient = new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpManager getInstance() {
        if (mOkHttpManager == null) {
            synchronized (OkHttpManager.class) {
                mOkHttpManager = new OkHttpManager();
            }
        }
        return mOkHttpManager;
    }

    public static void getAsync(String url, final Callback callback) {
        getInstance()._getAsync(url, callback);
    }

    private void _getAsync(String url, final Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(callback);
    }
}
