package netease.com.weather.ui.common;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import netease.com.weather.R;
import netease.com.weather.ui.base.BaseFragment;
import netease.com.weather.ui.view.AutoPlayTarget;
import netease.com.weather.util.StringUtils;

/**
 * Created by liu_shuai on 2016/11/30.
 */

public class ReplyImgFragment extends BaseFragment {
    private String mDirName;
    public final static String PARAM_DIR_NAME = "param_dir_name";
    private ImgAdapter mImgAdapter;
    private ImgSelectedCallback mImgSelectedCallback;

    public interface ImgSelectedCallback {
        void onSelected(String text);
    }

    public  void registerImgSelectedCallback(ImgSelectedCallback callback) {
        if (callback != null){
            mImgSelectedCallback = callback;
        }
    }

    public void unRegisterImgSelectedCallback() {
        mImgSelectedCallback = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDirName = getArguments().getString(PARAM_DIR_NAME);
            mImgAdapter = new ImgAdapter(getContext(), mDirName, mImgSelectedCallback);
        }

    }

    @Override
    public void onDestroyView() {
        unRegisterImgSelectedCallback();
        super.onDestroyView();
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
        private ImgSelectedCallback mImgSelectedCallback;

        public ImgAdapter(Context context, String dirName, ImgSelectedCallback callback) {
            mLayoutInflater = LayoutInflater.from(context);
            mDirName = dirName;
            mImgSelectedCallback = callback;
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
            Glide.with(view.getContext()).load(path).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new AutoPlayTarget(holder.img, false));

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

            view.setTag(R.id.tag_img, mImgPaths.get(i));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = (String) view.getTag(R.id.tag_img);
                    if (!TextUtils.isEmpty(name)) {
                        String[] ems = name.split("\\.");
                        if (ems.length == 2)
                        mImgSelectedCallback.onSelected("[" + ems[0] + "]");
                    }
                }
            });

            return view;
        }

        private final class ViewHolder {
            public ImageView img;
        }
    }
}
