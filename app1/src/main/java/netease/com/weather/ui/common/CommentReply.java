package netease.com.weather.ui.common;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
            mEditText = (EditText) v.findViewById(R.id.reply_edit);
            mReplyContainer = v.findViewById(R.id.comment_reply);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reply:
                send();
                break;
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
