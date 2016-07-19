package netease.com.weather.ui.biz.launchmode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import netease.com.weather.R;

/**
 * Created by user on 16-6-15.
 */

public class AActivity extends AppCompatActivity {

    @Bind(R.id.start)
    Button mStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_a);
        ButterKnife.bind(this);
    }

}
