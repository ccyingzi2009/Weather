package netease.com.weather.ui.biz.pc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import netease.com.weather.R;
import netease.com.weather.data.event.LoginEvent;
import netease.com.weather.data.model.UserBean;
import netease.com.weather.ui.base.BaseFragment;
import netease.com.weather.ui.base.constants.Constants;
import netease.com.weather.util.request.BaseRequest;
import netease.com.weather.util.request.JsonRequest;
import netease.com.weather.util.request.VolleyUtils;

/**
 * Created by liu_shuai on 16/8/28.
 */
public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.userName)
    TextView mUserName;
    private UserBean mUserBean;


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        initView();
        mAvatar.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    private void initView() {
        if (AccountModel.isLogin()) {
            mUserBean = AccountModel.getUserBean();
            Glide.with(this).load(mUserBean.getFace_url())
                    .asBitmap()
                    .centerCrop()
                    .placeholder(R.drawable.avatar_default)
                    .into(new BitmapImageViewTarget(mAvatar) {// 如何设置圆角
                        @Override
                        protected void setResource(Bitmap resource) {
                            super.setResource(resource);
                            RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                            drawable.setCircular(true);
                            mAvatar.setImageDrawable(drawable);
                        }
                    });

            String userName = mUserBean.getUser_name();
            mUserName.setText(userName);


        } else {
            mAvatar.setImageResource(R.drawable.avatar_default);
        }
    }


    public void onEventMainThread(LoginEvent event) {
        if (event == null || TextUtils.isEmpty(event.getUserId())) {
            return;
        }
        String url = String.format(Constants.URL_USER_QUERY, event.getUserId());
        VolleyUtils.addRequest(new JsonRequest<UserBean>(url, new TypeToken<UserBean>() {
        }, new BaseRequest.IResponseListener<UserBean>() {
            @Override
            public void onResponse(UserBean response) {
                AccountModel.saveAccount(response);
                initView();
            }

            @Override
            public void onError(VolleyError error) {
            }
        }), this);
    }


    @Override
    public void onClick(View view) {
        if (!AccountModel.isLogin()) {
            Intent i = new Intent(getContext(), LoginActivity.class);
            startActivity(i);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
