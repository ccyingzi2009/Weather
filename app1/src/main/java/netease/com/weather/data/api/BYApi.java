package netease.com.weather.data.api;

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
        api = new Retrofit.Builder()
                .baseUrl(BYService.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(BYService.class);
    }

}
