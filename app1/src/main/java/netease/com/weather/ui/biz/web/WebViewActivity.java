package netease.com.weather.ui.biz.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import netease.com.weather.R;
import netease.com.weather.ui.base.BaseActivity;
import netease.com.weather.ui.biz.article.ArticleNewFragment;

/**
 * Created by liu_shuai on 2016/12/21.
 */

public class WebViewActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Bundle args = getIntent().getExtras();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
        if (fragment != null) {
            if (fragment.isDetached()) {
                ft.attach(fragment);
            }
        } else {
            Fragment f = Fragment.instantiate(this, WebViewFragment.class.getName());
            f.setArguments(args);
            ft.add(R.id.fragmentContainer, f);
            ft.commit();
        }
    }
}
