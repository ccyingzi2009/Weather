package netease.com.weather.data.api;


import android.util.Base64;

import netease.com.weather.data.model.TopTen;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by liu_shuai on 16/3/19.
 */
public interface BYService {

    String BASE = "http://api.byr.cn";
    String END = ".json?appkey=9c479ffea0d8b38d&";

    String auth = "Basic " + Base64.encodeToString(("ccyingzi2009:liushuai").getBytes(), Base64.NO_WRAP);

    @GET("/widget/topten" + END)
    Call<TopTen> getTopTen(@Header("Authorization") String authorization);

    @GET("/article/{name}/{id}" + END)
    Call<TopTen> getArticle(@Path("name") String name, @Path("id") String id, @Query("count") int count, @Query("page") int page);

}
