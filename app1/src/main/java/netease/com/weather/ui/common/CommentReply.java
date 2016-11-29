package netease.com.weather.ui.common;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.stetho.common.StringUtil;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import netease.com.weather.R;
import netease.com.weather.data.model.CommentResponseBean;
import netease.com.weather.ui.base.BaseFragment;
import netease.com.weather.ui.base.constants.Constants;
import netease.com.weather.ui.biz.pc.AccountModel;
import netease.com.weather.util.StringUtils;
import netease.com.weather.util.request.BaseRequest;
import netease.com.weather.util.request.CommentReplyRequest;
import netease.com.weather.util.request.VolleyUtils;
import pl.droidsonroids.gif.GifDrawable;

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
            mReplyContainer = v.findViewById(R.id.comment_reply);
            mViewPager = (ViewPager) v.findViewById(R.id.viewPager);
            if (mAdapter == null) {
                mAdapter = new PageAdapter(activity.getSupportFragmentManager());
            }
            mViewPager.setAdapter(mAdapter);
        }
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

    public static class ReplyImgFragment extends BaseFragment {

        private String mDirName;
        public final static String PARAM_DIR_NAME = "param_dir_name";
        private ImgAdapter mImgAdapter;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                mDirName = getArguments().getString(PARAM_DIR_NAME);
                mImgAdapter = new ImgAdapter(getContext(), mDirName);
            }

        }

        @Override
        protected int getContentViewId() {
            return R.layout.fragment_reply_img;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            GridView gridView = (GridView) view.findViewById(R.id.girdView);
            gridView.setAdapter(mImgAdapter);

            AssetManager assertManager = getActivity().getAssets();
            try {
                String[] imgs = assertManager.list("img/" + mDirName);
                List<String> imgPaths = Arrays.asList(imgs);
                mImgAdapter.setImgPaths(imgPaths);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static class ImgAdapter extends BaseAdapter {

            private List<String> mImgPaths = new ArrayList<>();
            private LayoutInflater mLayoutInflater;
            private String mDirName;

            public ImgAdapter(Context context, String dirName) {
                mLayoutInflater = LayoutInflater.from(context);
                mDirName = dirName;
            }

            public void setImgPaths(List<String> imgPaths) {
                if (imgPaths != null && !imgPaths.isEmpty()) {
                    mImgPaths.clear();
                    mImgPaths.addAll(imgPaths);
                    notifyDataSetChanged();
                }
            }

            @Override
            public int getCount() {
                return mImgPaths.size();
            }

            @Override
            public Object getItem(int i) {
                return mImgPaths.get(i);
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                final ViewHolder holder;
                if (view == null) {
                    view = mLayoutInflater.inflate(R.layout.fragment_reply_img_item, viewGroup, false);
                    holder = new ViewHolder();
                    holder.img = (ImageView) view.findViewById(R.id.img);
                    view.setTag(holder);
                } else {
                    holder = (ViewHolder) view.getTag();
                }

                String path = StringUtils.ASSERTS_PATH + "img/" + mDirName + "/" + mImgPaths.get(i);
                Glide.with(view.getContext()).load(path).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.img);

                holder.img.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        final int action = motionEvent.getAction();
                        if (!(action == MotionEvent.ACTION_DOWN
                                || action == MotionEvent.ACTION_UP
                                || action == MotionEvent.ACTION_CANCEL)) return false;
                        Drawable drawable = holder.img.getDrawable();
                        if (drawable == null) {
                            return false;
                        }
                        GifDrawable gif = null;
                        if (drawable instanceof GifDrawable) {
                            gif = (GifDrawable) drawable;
                        }
                        if (gif == null) {
                            return false;
                        }
                        switch (action) {
                            case MotionEvent.ACTION_DOWN:
                                gif.start();
                                break;
                            case MotionEvent.ACTION_UP:
                            case MotionEvent.ACTION_CANCEL:
                                gif.stop();
                                break;
                        }
                        return false;
                    }
                });

                return view;
            }

            private final class ViewHolder {
                public ImageView img;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reply:
                send();
                break;
            case R.id.add_emoji:
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
