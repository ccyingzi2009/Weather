package netease.com.weather.util.api;


import android.util.Base64;

import java.util.List;

import netease.com.weather.model.TopTen;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by liu_shuai on 16/3/19.
 */
public interface BYService {

    String BASE = "http://api.byr.cn";
    String END = ".json?appkey=9c479ffea0d8b38d&";

    String auth = "Basic "+ Base64.encodeToString(("guest:").getBytes(), Base64.DEFAULT).split("\n")[0];

    @GET("/widget/topten" + END)
    Call<TopTen> getTopTen(@Header("Authorization") String authorization);
}
