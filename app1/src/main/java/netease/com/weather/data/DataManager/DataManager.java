package netease.com.weather.data.DataManager;

import netease.com.weather.data.BaseDataManager;
import netease.com.weather.data.api.BYApi;
import netease.com.weather.data.api.BYService;
import netease.com.weather.data.model.Article;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 16-4-21.
 */
public abstract class DataManager extends BaseDataManager<Article> {

    public void loadData(String name, String id, int page, int count) {

        Call<Article> article = BYApi.get().getApi().getArticle(BYService.auth, );
        article.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                onDataLoaded(response.body());
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {

            }
        })
    }
}
