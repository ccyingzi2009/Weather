package netease.com.weather.ui.biz.pc;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import butterknife.BindView;
import butterknife.ButterKnife;
import netease.com.weather.R;
import netease.com.weather.data.model.UserBean;
import netease.com.weather.ui.MainActivity;
import netease.com.weather.ui.base.BaseActivity;

/**
 * Created by liu_shuai on 16/8/28.
 */
public class ProfileActivity extends BaseActivity {

    @BindView(R.id.profile_avatar)
    ImageView profileAvatar;
    private String mUserId;
    private UserBean mUserBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            mUserId = args.getString(AccountModel.PARAM_USERID);
        }

        if (!TextUtils.isEmpty(mUserId)) {
            if (AccountModel.isLogin()) {
                mUserBean = AccountModel.getUserBean();
            }
        }

        initView();

    }

    private void initView() {
        Glide.with(this).load(mUserBean.getFace_url())
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(profileAvatar) {// 如何设置圆角
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                        drawable.setCircular(true);
                        profileAvatar.setImageDrawable(drawable);
                    }
                });
    }


}
