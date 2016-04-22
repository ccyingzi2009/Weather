package netease.com.weather.data.DataManager;

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
    private int mCount;

    Call<Article> articleCall;

    public DataManager(String name, String id, int count) {
        super();
        this.mCount = count;
        this.mId = id;
        this.mName = name;
    }

    @Override
    protected void loadData(final int page) {
        super.loadData(page);
        articleCall = BYApi.get().getApi().getArticle(BYService.auth
                , mName, mId, mCount, page);
        articleCall.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                if (response.isSuccess()) {
                    onDataLoaded(response.body(), page == 0);
                    moreDataAble = (response.body().getArticle().size() == BYService.PER_PAGE_DEFAULT);
                    loadFinish();
                }else {
                    failure();
                }
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                failure();
            }

            private void failure() {
                loadFinish();
                moreDataAble = false;
                articleCall = null;
            }
        });
    }
}
