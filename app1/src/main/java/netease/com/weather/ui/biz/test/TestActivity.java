package netease.com.weather.ui.biz.test;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import butterknife.BindView;
import butterknife.ButterKnife;
import netease.com.weather.R;
import netease.com.weather.ui.base.BaseActivity;

/**
 * Created by user on 16-8-29.
 */
public class TestActivity extends BaseActivity {

    @BindView(R.id.img)
    ImageView mImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        Glide.with(this)
                .load("http://att.newsmth.net/nForum/att/Picture/1388483/3387/large")
                .transform(new BitmapTransformation(TestActivity.this) {
                    @Override
                    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
                        System.out.println(outWidth + " "+ outHeight);
                        return null;
                    }

                    @Override
                    public String getId() {
                        return null;
                    }
                })
                .centerCrop()
                .into(mImg);

    }
}
