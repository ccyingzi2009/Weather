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
public abstract class DataManager extends PageDataManager<Article> {

    private String mName;
    private String mId;
    private int mPage = 0;
    private int mCount;

    public DataManager(String name, String id, int count) {
        this.mCount = count;
        this.mId = id;
        this.mName = name;
    }

    @Override
    protected void loadData(int page) {
        super.loadData(page);
        Call<Article> article = BYApi.get().getApi().getArticle(BYService.auth
                , mName, mId, mPage, mCount);
        article.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                onDataLoaded(response.body());
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {

            }
        });
    }
}
