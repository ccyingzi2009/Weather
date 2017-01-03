package netease.com.weather.ui.biz.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import netease.com.weather.R;
import netease.com.weather.ui.MainActivity;
import netease.com.weather.ui.base.BaseActivity;

/**
 * Created by user on 17-1-3.
 */

public class AdActivity extends BaseActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(AdActivity.this, MainActivity.class));
                AdActivity.this.finish();
            }
        }, 1500);

    }
}
