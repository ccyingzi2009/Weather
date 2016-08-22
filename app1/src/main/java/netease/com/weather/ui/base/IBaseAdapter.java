package netease.com.weather.ui.base;

import java.util.List;

/**
 * Created by liu_shuai on 16/8/22.
 */
public interface IBaseAdapter<T> {
    public List<T> getDatas();

    void notifyDataSetC();

    class Util {
        public static <T> void addDataForRefresh(IBaseAdapter<T> adapter, List<T> datas) {
            adapter.getDatas().clear();
            adapter.getDatas().addAll(datas);
        }

        public static <T> void addDataForMore(IBaseAdapter<T> adapter, List<T> datas) {
            adapter.getDatas().addAll(datas);
        }

    }

}
