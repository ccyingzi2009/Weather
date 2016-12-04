package netease.com.weather.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import netease.com.weather.R;
import netease.com.weather.ui.base.BaseActivity;
import netease.com.weather.ui.biz.board.BoardListFragment;
import netease.com.weather.ui.biz.main.SampleFragment;
import netease.com.weather.ui.biz.pc.ProfileFragment;
import netease.com.weather.ui.biz.test.TestFragment;
import netease.com.weather.util.SystemBarTintManager;


public class MainActivity extends BaseActivity {

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        animateToolbar();

        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), this);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    //case R.id.tab_1:
                    case R.id.tab_1:
                        mViewPager.setCurrentItem(0, false);
                        break;
                    case R.id.tab_2:
                        mViewPager.setCurrentItem(1, false);
                        break;
                    case R.id.tab_3:
                        mViewPager.setCurrentItem(2, false);
                        break;

                }
            }
        });


        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (bottomBar != null) {
                    bottomBar.selectTabAtPosition(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    static class MainPagerAdapter extends FragmentStatePagerAdapter {
        private Context mContext;

        public MainPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = Fragment.instantiate(mContext, SampleFragment.class.getName());
                    break;
                case 1:
                    fragment = Fragment.instantiate(mContext, BoardListFragment.class.getName());
                    break;
                case 2:
                    fragment = Fragment.instantiate(mContext, ProfileFragment.class.getName());
                    break;
                default:
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    /**
     * Toolbar的title动画
     */
    private void animateToolbar() {
        // this is gross but toolbar doesn't expose it's children to animate them :(
        View t = mToolbar.getChildAt(0);
        if (t != null && t instanceof TextView) {
            TextView title = (TextView) t;

            // fade in and space out the title.  Animating the letterSpacing performs horribly so
            // fake it by setting the desired letterSpacing then animating the scaleX ¯\_(ツ)_/¯
            title.setAlpha(0f);
            title.setScaleX(0.8f);

            title.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .setStartDelay(300)
                    .setDuration(900)
                    .setInterpolator(new LinearOutSlowInInterpolator());
        }
    }

}
/*    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

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

        initNaviView();

    }

    //初始化导航栏
    private void initNaviView() {
        navView.inflateHeaderView(R.layout.nav_header_main);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_settings:
                        startActivity(new Intent(MainActivity.this, TestActivity.class));
                        break;
                    case R.id.nav_about:
                        checkUpdate();
                        break;
                    case R.id.nav_board:
                        startActivity(new Intent(MainActivity.this, BoardActivity.class));
                        break;
                }
                return false;
            }
        });

        View headerView = navView.getHeaderView(0);
        final ImageView imageView = (ImageView) headerView.findViewById(R.id.imageView);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AccountModel.isLogin()) {
                        Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                        i.putExtra(AccountModel.PARAM_USERID, AccountModel.getUserId());
                        startActivity(i);
                    }else {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                }
            });
        }
        updateUserInfo();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    //检测更新
    private void checkUpdate() {
        VolleyUtils.addRequest(new JsonRequest<>(Constants.URL_UPDATE, new TypeToken<UpdateBean>() {
        }, new BaseRequest.IResponseListener<UpdateBean>() {
            @Override
            public void onResponse(UpdateBean response) {
                int versionCode = SystemUtils.getVersionCode();
                int updateVersionCode = response.getVersionCode();
                if (updateVersionCode > versionCode) {
                    showUpdateDialog(response);
                } else {
                    showSnackBar(getResources().getString(R.string.update_check_no_update));
                }
            }

            @Override
            public void onError(VolleyError error) {
            }
        }), this);
    }

    private void showUpdateDialog(final UpdateBean response) {
        new MaterialDialog.Builder(this).title(response.getTitle())
                .positiveText(getResources().getString(R.string.update_check_update))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(MainActivity.this, VersionUpdateService.class);
                        intent.putExtra(VersionUpdateModel.UPDATE_URL, response.getUrl());
                        startService(intent);
                    }
                })
                .negativeText(getResources().getString(R.string.update_check_cancel))
                .content(response.getContent())
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawers();
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void showSnackBar(String content) {
        Snackbar.make(drawerLayout, content, Snackbar.LENGTH_SHORT)
                .setDuration(2000)
                .show();
    }


    *//**
 * 登陆完成事件
 *//*
    public void onEventMainThread(LoginEvent event) {
        if (event == null || TextUtils.isEmpty(event.getUserId())) {
            return;
        }
        String url = String.format(Constants.URL_USER_QUERY, event.getUserId());
        VolleyUtils.addRequest(new JsonRequest<>(url, new TypeToken<UserBean>() {
        }, new BaseRequest.IResponseListener<UserBean>() {
            @Override
            public void onResponse(UserBean response) {
                AccountModel.saveAccount(response);
                updateUserInfo();
            }

            @Override
            public void onError(VolleyError error) {
            }
        }), this);
    }

    //更新首页用户信息(头像、昵称)
    private void updateUserInfo() {
        if (navView == null) {
            return;
        }
        View headerView = navView.getHeaderView(0);
        final ImageView imageView = (ImageView) headerView.findViewById(R.id.imageView);

        if (AccountModel.isLogin()) { //初始化个人信息
            UserBean userBean = AccountModel.getUserBean();
            if (userBean != null) {
                //头像
                 imageView != null;
                Glide.with(MainActivity.this).load(userBean.getFace_url())
                        .asBitmap()
                        .centerCrop()
                        .into(new BitmapImageViewTarget(imageView) {// 如何设置圆角
                            @Override
                            protected void setResource(Bitmap resource) {
                                super.setResource(resource);
                                RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                                drawable.setCircular(true);
                                imageView.setImageDrawable(drawable);
                            }
                        });
                //用户名
                TextView userName = (TextView) headerView.findViewById(R.id.userName);
                if (userName != null) {
                    userName.setText(userBean.getUser_name());
                }
            }
        }
    }
}*/
