package netease.com.weather.ui.common;


import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by YC on 14-12-30.
 */
public class AdapterHandler {

    public static void notifyDataSetChanged(RecyclerView.Adapter adapter, List data) {
        notifyDataSetChanged(adapter, data, true);
    }

    public static void notifyDataSetChanged(RecyclerView.Adapter adapter, List data, boolean refresh) {
        if (adapter == null || data == null) {
            return;
        }
        if (adapter instanceof ListAdapter) {
            List origData = ((ListAdapter) adapter).getData();
            if (refresh) {
                origData.clear();
            }
            origData.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }
}
