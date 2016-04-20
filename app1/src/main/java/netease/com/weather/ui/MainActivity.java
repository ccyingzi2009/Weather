package netease.com.weather.ui;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarFragment;

import netease.com.weather.R;
import netease.com.weather.data.model.TopTen;
import netease.com.weather.ui.base.BaseActivity;
import netease.com.weather.ui.biz.SampleFragment;
import netease.com.weather.ui.biz.TopTenFragment;
import netease.com.weather.data.api.BYApi;
import netease.com.weather.data.api.BYService;
import retrofit2.Call;
import retrofit2.Callback;


public class MainActivity extends BaseActivity {

    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
        }


        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setFragmentItems(getSupportFragmentManager(), R.id.fragmentContainer,
                new BottomBarFragment(TopTenFragment.newInstance(), R.drawable.ic_recents, "Top10"),
                new BottomBarFragment(SampleFragment.newInstance("Content for favorites."), R.drawable.ic_favorites, "Favorites"),
                new BottomBarFragment(SampleFragment.newInstance("Content for nearby stuff."), R.drawable.ic_nearby, "Nearby"),
                new BottomBarFragment(SampleFragment.newInstance("Content for friends."), R.drawable.ic_friends, "Friends"),
                new BottomBarFragment(SampleFragment.newInstance("Content for food."), R.drawable.ic_restaurants, "Food")
        );

        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.mapColorForTab(1, 0xFF5D4037);
        mBottomBar.mapColorForTab(2, "#7B1FA2");
        mBottomBar.mapColorForTab(3, "#FF5252");
        mBottomBar.mapColorForTab(4, "#FF9800");

    }

    private void initData() {
        Call<TopTen> tops = BYApi.get().getApi().getTopTen(BYService.auth);
        tops.enqueue(new Callback<TopTen>() {
            @Override
            public void onResponse(Call<TopTen> call, retrofit2.Response<TopTen> response) {
                TopTen list = response.body();
                System.out.println(list.toString());
            }

            @Override
            public void onFailure(Call<TopTen> call, Throwable t) {
                System.out.println("failure");
            }
        });

    }
}
