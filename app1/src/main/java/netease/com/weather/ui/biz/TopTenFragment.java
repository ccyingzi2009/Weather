package netease.com.weather.ui.biz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import netease.com.weather.R;
import netease.com.weather.data.model.TopTen;
import netease.com.weather.ui.MainActivity;
import netease.com.weather.ui.base.BaseFragment;
import netease.com.weather.data.api.BYApi;
import netease.com.weather.data.api.BYService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liu_shuai on 16/3/19.
 */
public class TopTenFragment extends BaseFragment {

    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    private Top10ListAdapter mAdapter;
    private List<TopTen.ArticleEntity> mArticles = new ArrayList<>();

    public static TopTenFragment newInstance() {
        Bundle args = new Bundle();
        TopTenFragment top10Fragment = new TopTenFragment();
        top10Fragment.setArguments(args);
        return top10Fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new Top10ListAdapter((MainActivity) getActivity(), mArticles);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_ten, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycleView.setAdapter(mAdapter);
        initData();
    }

    private void initData() {
        Call<TopTen> tops = BYApi.get().getApi().getTopTen(BYService.auth);
        tops.enqueue(new Callback<TopTen>() {
            @Override
            public void onResponse(Call<TopTen> call, Response<TopTen> response) {
                TopTen top10 = response.body();
                List<TopTen.ArticleEntity> articleEntities = top10.getArticle();
                if (articleEntities != null && !articleEntities.isEmpty()) {
                    mArticles.clear();
                    mArticles.addAll(articleEntities);
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<TopTen> call, Throwable t) {
                System.out.println("failure");
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
