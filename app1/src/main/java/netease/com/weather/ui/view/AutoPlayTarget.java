package netease.com.weather.ui.view;

import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

/**
 * Created by liu_shuai on 2016/11/30.
 */

public class AutoPlayTarget extends GlideDrawableImageViewTarget{
    private boolean mAutoPlayGifs;
    public AutoPlayTarget(ImageView view, boolean autoPlayGifs) {
        super(view);
        mAutoPlayGifs = autoPlayGifs;
    }

    @Override
    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
        super.onResourceReady(resource, animation);
        if(!mAutoPlayGifs) {
            resource.stop();
        }
    }

    @Override
    public void onStart() {
        if (mAutoPlayGifs) {
            super.onStart();
        }
    }

    @Override
    public void onStop() {
        if (mAutoPlayGifs) {
            super.onStop();
        }
    }
}
