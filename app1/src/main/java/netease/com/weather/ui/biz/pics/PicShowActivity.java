package netease.com.weather.ui.biz.pics;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import netease.com.weather.R;
import netease.com.weather.ui.base.BaseActivity;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by user on 16-8-27.
 */
public class PicShowActivity extends BaseActivity implements View.OnClickListener, PhotoViewAttacher.OnPhotoTapListener {

    private ImageAdapter mAdapter;

    public static final String PIC_SHOW_IMGS = "images";
    public static final String PIC_SHOW_POSITION = "position";
    private int mPosition;
    private ArrayList<String> imgs;

    @Bind(R.id.pic_viewpager)
    ViewPager mViewPager;

    public final static String SCENE_IMAGE = "scene_image";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setBackgroundDrawableResource(android.R.color.black);
        setContentView(R.layout.common_pic_show_fragment);
        ButterKnife.bind(this);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            imgs = args.getStringArrayList(PIC_SHOW_IMGS);
            mPosition = args.getInt(PIC_SHOW_POSITION);

            mAdapter = new ImageAdapter(this, imgs, getLayoutInflater(), this);
        }
        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
            if (imgs != null && !imgs.isEmpty()) {
                toolbar.setTitle(mPosition + 1 + "/" + imgs.size());
            }
        }

        if (isLollipop()) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(
                              View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }

        mViewPager = (ViewPager) findViewById(R.id.pic_viewpager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(true, new CardTransformer(0.5f));
        mViewPager.setCurrentItem(mPosition);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                if (imgs != null && !imgs.isEmpty()) {
//                    ActionBar toolbar = getSupportActionBar();
//                    if (toolbar != null) {
//                        toolbar.setTitle(position + 1 + "/" + imgs.size());
//                    }
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onDestroy() {
        if (mViewPager != null) {
            mViewPager = null;
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onPhotoTap(View view, float v, float v1) {
        finish();
    }

    private static class ImageAdapter extends PagerAdapter {
        private Context mContext;
        private LayoutInflater mInflater;
        private PhotoViewAttacher.OnPhotoTapListener mListener;

        private List<String> mImageUrl = new ArrayList<>();
        private ArrayList<String> images;

        public ImageAdapter(Context context, ArrayList<String> imgs, LayoutInflater inflater, PhotoViewAttacher.OnPhotoTapListener listener) {
            this.mContext = context;
            this.mInflater = inflater;
            this.mListener = listener;
            this.images = imgs;

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mInflater.inflate(R.layout.comm_pic_show_fragment_item, container, false);

//            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT);
            final PhotoView imageView = (PhotoView) view.findViewById(R.id.imageview);

            PhotoView photoView = new PhotoView(mContext);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

            final ProgressBar bar = (ProgressBar) view.findViewById(R.id.progress_bar);
            final PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);
            bar.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(images.get(position))
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            Log.d("Glide=====", "onException");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            Log.d("Glide=====", "onResourceReady");
                            //bar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .into(new GlideDrawableImageViewTarget(photoView));

            attacher.setOnPhotoTapListener(mListener);

            return photoView;
        }

        @Override
        public int getCount() {
            return images == null ? 0 : images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            PageItem item = (PageItem) object;
//            item.mAttacher.cleanup();
//            container.removeView(item.mView);
//            item.clear();

            container.removeView((View) object);
        }


    }

    private static class PageItem {
        private View mView;
        private PhotoViewAttacher mAttacher;

        public PageItem(View mView, PhotoViewAttacher mAttacher) {
            this.mAttacher = mAttacher;
            this.mView = mView;
        }

        public void clear() {
            mView = null;
            mAttacher = null;
        }
    }

    /**
     * Awesome Launcher-inspired page transformer
     */
    private static class CardTransformer implements ViewPager.PageTransformer {

        private final float scaleAmount;

        public CardTransformer(float scalingStart) {
            scaleAmount = 1 - scalingStart;
        }

        @Override
        public void transformPage(View page, float position) {
            if (position >= 0f) {
                final int w = page.getWidth();
                float scaleFactor = 1 - scaleAmount * position;

                page.setAlpha(1f - position);
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setTranslationX(w * (1 - position) - w);
            }
        }
    }
}
