package netease.com.weather.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import netease.com.weather.R;
import netease.com.weather.ui.base.BaseActivity;


public class MainActivity extends BaseActivity {

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    @BindView(R.id.contentContainer)
    FrameLayout contentContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        ButterKnife.bind(this);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

            }
        });

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
                assert imageView != null;
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
