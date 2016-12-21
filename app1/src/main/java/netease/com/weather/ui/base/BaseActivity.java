package netease.com.weather.ui.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;

import netease.com.weather.R;
import netease.com.weather.util.SystemBarTintManager;

/**
 * Created by liu_shuai on 16/4/20.
 */
public class BaseActivity extends AppCompatActivity {

    private MaterialDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        setupStatusBar();
    }

    private void setupStatusBar() {
        SystemBarTintManager tintManager = new SystemBarTintManager(this/*, mTintViewContainer*/);
        tintManager.setStatusBarTintEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.colorPrimary));
        }
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    protected boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    @Override
    protected void onDestroy() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
        super.onDestroy();
    }

    protected void showProgressDialog(int title) {
        mProgressDialog = new MaterialDialog.Builder(this)
                .title(title)
                .progress(true, 0)
                .show();
    }

    protected void showProgressDialog(int title, int content) {
        mProgressDialog = new MaterialDialog.Builder(this)
                .title(title)
                .content(content)
                .progress(true, 0)
                .show();
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
