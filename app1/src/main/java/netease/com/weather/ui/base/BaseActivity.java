package netease.com.weather.ui.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import netease.com.weather.ui.view.swipback.SwipeBackActivityBase;
import netease.com.weather.ui.view.swipback.SwipeBackActivityHelper;
import netease.com.weather.ui.view.swipback.SwipeBackLayout;
import netease.com.weather.ui.view.swipback.Utils;

/**
 * Created by liu_shuai on 16/4/20.
 */
public class BaseActivity extends AppCompatActivity implements SwipeBackActivityBase{

    private SwipeBackActivityHelper mHelper;

    private MaterialDialog mProgressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Nullable
    @Override
    public View findViewById(@IdRes int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
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
