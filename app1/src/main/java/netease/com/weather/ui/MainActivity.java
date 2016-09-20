package netease.com.weather.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import netease.com.weather.R;
import netease.com.weather.data.event.LoginEvent;
import netease.com.weather.data.model.UpdateBean;
import netease.com.weather.data.model.UserBean;
import netease.com.weather.ui.base.BaseActivity;
import netease.com.weather.ui.base.constants.Constants;
import netease.com.weather.ui.biz.main.SampleFragment;
import netease.com.weather.ui.biz.pc.LoginActivity;
import netease.com.weather.ui.biz.test.TestActivity;
import netease.com.weather.ui.biz.update.VersionUpdateModel;
import netease.com.weather.ui.biz.update.VersionUpdateService;
import netease.com.weather.util.SystemUtils;
import netease.com.weather.util.request.BaseRequest;
import netease.com.weather.util.request.JsonRequest;
import netease.com.weather.util.request.VolleyUtils;


public class MainActivity extends BaseActivity {

    @BindView(R.id.nav_view)
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
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

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


    public void onEventMainThread(LoginEvent event) {
        if (event == null || TextUtils.isEmpty(event.getUserId())) {
            return;
        }
        String url = String.format(Constants.URL_USER_QUERY, event.getUserId());
        VolleyUtils.addRequest(new JsonRequest<>(url, new TypeToken<UserBean>() {
        }, new BaseRequest.IResponseListener<UserBean>() {
            @Override
            public void onResponse(UserBean response) {
                View headerView = navView.inflateHeaderView(R.layout.nav_header_main);
                ImageView headImg = (ImageView) headerView.findViewById(R.id.imageView);

                String faceUrl = response.getFace_url();
                KLog.d(faceUrl);
                Glide.with(MainActivity.this).load(faceUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(headImg);
            }

            @Override
            public void onError(VolleyError error) {
            }
        }), this);
    }
}
