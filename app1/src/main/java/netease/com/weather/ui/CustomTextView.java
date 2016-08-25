package netease.com.weather.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import java.util.HashSet;
import java.util.Set;

import netease.com.weather.util.SystemUtils;

/**
 * Created by user on 16-8-25.
 */
public class CustomTextView extends TextView {


    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setText(String source) {
        setText(Html.fromHtml(source, new GlideImageGetter(this.getContext(), this), null));
    }

    public static class GlideImageGetter implements Html.ImageGetter, Drawable.Callback {

        private final Context mContext;

        private final TextView mTextView;

        private final Set<ImageGetterViewTarget> mTargets;

        public static GlideImageGetter get(View view) {
            return (GlideImageGetter) view.getTag();
        }

        public void clear() {
            GlideImageGetter prev = get(mTextView);
            if (prev == null) return;

            for (ImageGetterViewTarget target : prev.mTargets) {
                Glide.clear(target);
            }
        }

        public GlideImageGetter(Context context, TextView textView) {
            this.mContext = context;
            this.mTextView = textView;

            clear();
            mTargets = new HashSet<>();
            mTextView.setTag(this);
        }

        @Override
        public Drawable getDrawable(String url) {
            url = url.replace("/middle", "");
            final UrlDrawable urlDrawable = new UrlDrawable();

            Glide.with(mContext)
                    .load(url)
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new ImageGetterViewTarget(mTextView, urlDrawable));

            int maxHeight = SystemUtils.getScreenWidth(mContext) / 2;

            if (urlDrawable.getDrawable() == null) {
                urlDrawable.setBounds(0, 0, SystemUtils.getScreenWidth(mContext) * 9 / 16, maxHeight);
            }

            return urlDrawable;

        }

        @Override
        public void invalidateDrawable(Drawable who) {
            mTextView.invalidate();
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {

        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {

        }

        private class ImageGetterViewTarget extends ViewTarget<TextView, GlideDrawable> {

            private final UrlDrawable mDrawable;

            private ImageGetterViewTarget(TextView view, UrlDrawable drawable) {
                super(view);
                mTargets.add(this);
                this.mDrawable = drawable;
            }

            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                Rect rect;
                int maxHeight = SystemUtils.getScreenWidth(mContext) / 2;
                float intrinsicWidth = resource.getIntrinsicWidth();
                float intrinsicHeight = resource.getIntrinsicHeight();
                rect = new Rect(0, 0, (int) (maxHeight * intrinsicWidth / intrinsicHeight), maxHeight);

                resource.setBounds(rect);

                mDrawable.setBounds(rect);
                mDrawable.setDrawable(resource);


                if (resource.isAnimated()) {
                    mDrawable.setCallback(get(getView()));
                    resource.setLoopCount(GlideDrawable.LOOP_FOREVER);
                    resource.start();
                }

                getView().setText(getView().getText());
                getView().postInvalidate();
                System.out.println("invalidate times");
            }

            private Request request;

            @Override
            public Request getRequest() {
                return request;
            }

            @Override
            public void setRequest(Request request) {
                this.request = request;
            }
        }
    }

    static class UrlDrawable extends Drawable implements Drawable.Callback {

        private GlideDrawable mDrawable;

        @Override
        public void draw(Canvas canvas) {
            if (mDrawable != null) {
                mDrawable.draw(canvas);
            } else {
                System.out.println("UrlDrawable is empty");
            }
        }

        @Override
        public void setAlpha(int alpha) {
            if (mDrawable != null) {
                mDrawable.setAlpha(alpha);
            }
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            if (mDrawable != null) {
                mDrawable.setColorFilter(cf);
            }
        }

        @Override
        public int getOpacity() {
            if (mDrawable != null) {
                return mDrawable.getOpacity();
            }
            return 0;
        }

        public void setDrawable(GlideDrawable drawable) {
            if (this.mDrawable != null) {
                this.mDrawable.setCallback(null);
            }
            drawable.setCallback(this);
            this.mDrawable = drawable;
        }

        public Drawable getDrawable() {
            return mDrawable;
        }

        @Override
        public void invalidateDrawable(Drawable who) {
            if (getCallback() != null) {
                getCallback().invalidateDrawable(who);
            }
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
            if (getCallback() != null) {
                getCallback().scheduleDrawable(who, what, when);
            }
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
            if (getCallback() != null) {
                getCallback().unscheduleDrawable(who, what);
            }
        }

    }
}
