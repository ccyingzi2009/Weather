package netease.com.weather.ui.common;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import netease.com.weather.R;
import netease.com.weather.data.model.CommentResponseBean;
import netease.com.weather.ui.base.constants.Constants;
import netease.com.weather.ui.biz.pc.AccountModel;
import netease.com.weather.util.EmotionUtils;
import netease.com.weather.util.request.BaseRequest;
import netease.com.weather.util.request.CommentReplyRequest;
import netease.com.weather.util.request.VolleyUtils;

/**
 * Created by user on 16-10-27.
 */

public class CommentReply implements View.OnClickListener {

    private EditText mEditText;
    private String mArticleId;
    private String mBoardId;
    private ReplyCallback mReplyCallback;
    private AppCompatActivity mActivity;
    private View mReplyContainer;
    private ViewPager mViewPager;
    private PageAdapter mAdapter;

    public final static int PARAM_DELAY = 200;

    public void onPicSelected(String picName) {
        if (mEditText != null) {
            String currentText = mEditText.getText().toString();
            StringBuilder sb = new StringBuilder(currentText);
            int currentPosition = mEditText.getSelectionStart();
            sb.insert(currentPosition, picName);
            mEditText.setText(sb.toString());
            mEditText.setSelection(currentPosition + picName.length());

        }
    }

    public interface ReplyCallback {
        void onEdit();

        void onStartReply();

        void onReply(boolean success);
    }

    public void setOnReplyCallback(ReplyCallback callback) {
        if (callback != null) {
            mReplyCallback = callback;
        }
    }

    public CommentReply(AppCompatActivity activity, ViewGroup container) {
        mActivity = activity;
        init(activity, container);
    }


    private void init(AppCompatActivity activity, ViewGroup container) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        int layoutId = R.layout.biz_comment_reply_layout;
        View v = inflater.inflate(layoutId, container, true);
        if (v != null) {
            v.findViewById(R.id.reply).setOnClickListener(this);
            v.findViewById(R.id.add_emoji).setOnClickListener(this);
            mEditText = (EditText) v.findViewById(R.id.reply_edit);
            mEditText.setOnClickListener(this);
            mReplyContainer = v.findViewById(R.id.comment_reply);
            mViewPager = (ViewPager) v.findViewById(R.id.viewPager);
            if (mAdapter == null) {
                mAdapter = new PageAdapter(activity.getSupportFragmentManager());
            }
            mViewPager.setAdapter(mAdapter);

            final View root = mActivity.findViewById(android.R.id.content);
            if (root != null) {
                root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Log.d("CommentReply", "onGlobalLayout");
                        if (!isEmotionVisible()) {
                            setEmotionHeight(EmotionUtils.getInputHeight(mActivity));
                        }
                        if (EmotionUtils.isSoftInputShow(mActivity)) {
                            int height = EmotionUtils.getSoftInputHeight(mActivity);
                            if (height > 0) {
                                EmotionUtils.setInputHeight(height);
                            }
                        }
                    }
                });
            }
        }
    }

    public boolean isEmotionVisible() {
        if (mViewPager != null) {
            return mViewPager.getVisibility() == View.VISIBLE;
        }
        return false;
    }

    public static class PageAdapter extends FragmentPagerAdapter {

        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            String dirName = "em";
            switch (position) {
                case 0:
                    dirName = "em";
                    break;
                case 1:
                    dirName = "ema";
                    break;
                case 2:
                    dirName = "emb";
                    break;
                case 3:
                    dirName = "emc";
                    break;
            }
            Bundle args = new Bundle();
            args.putString(ReplyImgFragment.PARAM_DIR_NAME, dirName);
            ReplyImgFragment fragment = new ReplyImgFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reply:
                send();
                break;
            case R.id.add_emoji:
                if (mViewPager != null) {
                    if (mViewPager.getVisibility() == View.VISIBLE) {
                        EmotionUtils.showSoftInput(mActivity, mEditText);
                        v.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                hideEmotionView();

                            }
                        }, PARAM_DELAY);
                    } else {
                        showEmotionView();
                        EmotionUtils.hideSoftInput(mActivity, mEditText);
                    }

                }
                break;
            case R.id.reply_edit:
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideEmotionView();
                    }
                }, PARAM_DELAY);
                break;
        }
    }

    private void setEmotionHeight(int height) {
        ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
        params.height = height;
    }

    private void showEmotionView() {
        if (mViewPager != null) {
            mViewPager.setVisibility(View.VISIBLE);
        }
        if (mActivity != null) {
            EmotionUtils.updateSoftInputMethod(mActivity, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        }
    }

    private void hideEmotionView() {
        if (mViewPager != null) {
            mViewPager.setVisibility(View.GONE);
        }
        if (mActivity != null) {
            EmotionUtils.updateSoftInputMethod(mActivity, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }

    public void ready(String boardId, String articleId) {
        mBoardId = boardId;
        mArticleId = articleId;
    }

    //发送
    private void send() {

        if (!AccountModel.isLogin()) {
            AccountModel.gotoLogin(mActivity);
            return;
        }

        if (mEditText != null) {
            String content = mEditText.getText().toString();
            if (!TextUtils.isEmpty(content)) {
                Map<String, String> param = new HashMap<>();
                param.put("content", content);
                param.put("id", mArticleId);//// TODO: 文章id为空判断
                param.put("subject", "Re:");//// TODO
                String url = String.format(Constants.URL_REPLY, mBoardId);
                CommentReplyRequest request = new CommentReplyRequest(url, param, new TypeToken<CommentResponseBean>() {
                });
                request.setResponseListener(new BaseRequest.IResponseListener<CommentResponseBean>() {
                    @Override
                    public void onResponse(CommentResponseBean response) {
                        if (response.getAjax_st() == 1) {
                            //回复成功
                            if (mReplyCallback != null) {
                                mReplyCallback.onReply(true);
                            }

                            //初始化界面。
                            resetReplyView();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        //回复失败
                        if (mReplyCallback != null) {
                            mReplyCallback.onReply(false);
                        }
                    }
                });
                VolleyUtils.addRequest(request, this);

                if (mReplyCallback != null) {
                    mReplyCallback.onStartReply();
                }
            } else {

            }
        }
    }


    private void resetReplyView() {
        if (mEditText != null) {
            mEditText.getText().clear();
            if (mEditText.hasFocus()) {
                mEditText.clearFocus();
            }
            mEditText.setFocusableInTouchMode(true);

        }

        if (mReplyContainer != null) {
            mReplyContainer.setFocusable(true);
        }
        hideSoftInput();
    }

    private void hideSoftInput() {
        if (mActivity != null && mEditText != null) {
            //收起键盘
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0); //强制隐藏键盘
        }
    }
}
