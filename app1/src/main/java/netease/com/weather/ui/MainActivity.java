package netease.com.weather.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import netease.com.weather.R;
import netease.com.weather.ui.base.BaseActivity;
import netease.com.weather.ui.biz.main.SampleFragment;
import netease.com.weather.ui.biz.pc.LoginActivity;


public class MainActivity extends BaseActivity {

    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
        if (fragment != null) {
            if (fragment.isDetached()) {
                ft.attach(fragment);
            }
        } else {
            SampleFragment sampleFragment = SampleFragment.newInstance("");
            ft.add(R.id.fragmentContainer, sampleFragment);
            ft.commit();

        }

        //首页禁止手势返回
        setSwipeBackEnable(false);

        View headerView = navView.inflateHeaderView(R.layout.nav_header_main);
        //todo headerView
        ImageView imageView = (ImageView) headerView.findViewById(R.id.imageView);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });
        }


//        mBottomBar = BottomBar.attach(this, savedInstanceState);
//        mBottomBar.setFragmentItems(getSupportFragmentManager(), R.id.fragmentContainer,
//                new BottomBarFragment(SampleFragment.newInstance(""), R.drawable.ic_recents, "Top10"),
//                new BottomBarFragment(ArticleFragment.newInstance(), R.drawable.ic_favorites, "Favorites"),
//                new BottomBarFragment(CustomViewFragment.newInstance(), R.drawable.ic_nearby, "Nearby"),
//                new BottomBarFragment(SampleFragment.newInstance("Content for friends."), R.drawable.ic_friends, "Friends"),
//                new BottomBarFragment(SampleFragment.newInstance("Content for food."), R.drawable.ic_restaurants, "Food")
//        );
//
//        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));
//        mBottomBar.mapColorForTab(1, 0xFF5D4037);
//        mBottomBar.mapColorForTab(2, "#7B1FA2");
//        mBottomBar.mapColorForTab(3, "#FF5252");
//        mBottomBar.mapColorForTab(4, "#FF9800");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawers();
                }else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
