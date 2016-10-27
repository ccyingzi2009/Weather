package netease.com.weather.ui.biz.article;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.transition.Fade;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import netease.com.weather.R;
import netease.com.weather.ui.base.BaseActivity;
import netease.com.weather.ui.common.CommentReply;

/**
 * Created by user on 16-4-21.
 */
public class ArticleActivity extends BaseActivity implements CommentReply.ReplyCallback {

    @BindView(R.id.replyContainer)
    FrameLayout mReplyContainer;
    private CommentReply mReply;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade explode = new Fade();
            explode.setDuration(500);
            getWindow().setEnterTransition(explode);
        }

        Bundle args = getIntent().getExtras();
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
        String articleId = args.getString(ArticleModel.ARTICLE_ID);
        String boardId = args.getString(ArticleModel.ARTICLE_BOARDID);
        mReply = new CommentReply(this, mReplyContainer);
        mReply.ready(boardId, articleId);
        mReply.setOnReplyCallback(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onEdit() {

    }

    @Override
    public void onStartReply() {
        showProgressDialog(R.string.comment_loading);
    }

    @Override
    public void onReply(boolean success) {
        dismissProgressDialog();
        if (success) {
            //成功
            Toast.makeText(this, "回复成功", Toast.LENGTH_SHORT).show();
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if (fragment instanceof ArticleNewFragment) {
                ((ArticleNewFragment) fragment).replySuccess();
            }
        }
    }
}
