package netease.com.weather.data.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liu_shuai on 16/3/19.
 */
public class BYApi {

    private static volatile BYApi singleton;

    private BYService api;


    public static BYApi get() {
        if (singleton == null) {
            synchronized (BYApi.class) {
                singleton = new BYApi();
            }
        }
        return singleton;
    }

    public BYService getApi() {
        if (api == null) {
            create();
        }
        return api;
    }

    private void create() {
        OkHttpClient client = new OkHttpClient.Builder().
                addNetworkInterceptor(new StethoInterceptor())
                .build();
        api = new Retrofit.Builder()
                .client(client)
                .baseUrl(BYService.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(BYService.class);
    }

}
