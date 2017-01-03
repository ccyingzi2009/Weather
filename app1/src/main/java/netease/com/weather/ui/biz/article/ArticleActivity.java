package netease.com.weather.ui.biz.article;

import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import netease.com.weather.R;
import netease.com.weather.data.event.PicSelectEvent;
import netease.com.weather.ui.base.BaseActivity;
import netease.com.weather.ui.common.CommentReply;
import netease.com.weather.util.SystemUtils;

/**
 * Created by user on 16-4-21.
 */
public class ArticleActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getIntent().getExtras();
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);

        //fideStatus();
        //mTitleBar.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setupActionbar(mToolbar);
        setActionbarTitle(args.getString(ArticleModel.ARTICLE_TITLE));
        SystemUtils.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary));
        //ViewCompat.setTransitionName(mTitle, "title");
        //mTitle.setText(args.getString(ArticleModel.ARTICLE_TITLE));


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
        if (fragment != null) {
            if (fragment.isDetached()) {
                ft.attach(fragment);
            }
        } else {
            Fragment f = Fragment.instantiate(this, ArticleNewFragment.class.getName());
            f.setArguments(args);
            ft.add(R.id.fragmentContainer, f);
            ft.commit();
        }


    }


    //statusbar颜色淡化
    private void fideStatus() {
        if (isLollipop()) {
            ValueAnimator statusBarColorAnimate = ValueAnimator.ofArgb(getWindow().getStatusBarColor(), android.R.color.transparent);
            statusBarColorAnimate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    SystemUtils.setStatusBarColor(ArticleActivity.this, (int) valueAnimator.getAnimatedValue());
                }
            });
            statusBarColorAnimate.setDuration(400);
            statusBarColorAnimate.start();
        }
    }
}
