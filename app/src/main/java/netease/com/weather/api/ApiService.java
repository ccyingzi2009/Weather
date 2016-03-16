package netease.com.weather.api;

import netease.com.weather.model.MainPage;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by liu_shuai on 16/3/8.
 */
public interface ApiService {
    //String BASE_END = ".json?appkey=9c479ffea0d8b38d";
    /*//十大  widget/topten
    //@Headers("Accept-Encoding: gzip, deflate")
    @GET("widget/topten.json?appkey=9c479ffea0d8b38d")
    Call<TopTen> getTopTen(@Header("Authorization") String authorization);

    @GET("http://c.m.163.com/nc/attitude/attitudeBoard.html")
    Call<Attitude> getAttitude();*/

    //鼠绘
    String BASE_BASE = "http://www.ishuhui.com/";
    @GET("http://www.ishuhui.com/")
    Call<MainPage> getMainTitle();

}
