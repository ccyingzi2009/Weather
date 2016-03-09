package netease.com.weather.api;

import netease.com.weather.model.Attitude;
import netease.com.weather.model.TopTen;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by liu_shuai on 16/3/8.
 */
public interface ApiService {

    String BASE_BASE = "http://api.byr.cn/";
    String BASE_END = ".json?appkey=9c479ffea0d8b38d";

    //十大  widget/topten
    //@Headers("Accept-Encoding: gzip, deflate")
    @GET("widget/topten.json?appkey=9c479ffea0d8b38d")
    Call<TopTen> getTopTen(@Header("Authorization") String authorization);

    @GET("http://c.m.163.com/nc/attitude/attitudeBoard.html")
    Call<Attitude> getAttitude();
}
