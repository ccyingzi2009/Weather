package netease.com.weather.data.DataManager;

import netease.com.weather.data.BaseDataManager;

/**
 * Created by liu_shuai on 16/4/21.
 */
public class PageDataManager<T> extends BaseDataManager<T> {

    private int page = 0;

    @Override
    public void onDataLoaded(T data) {

    }

    public void loadData() {
        loadData(page);
        page++;
    }

    protected void loadData(int page) {
    }
}
