package netease.com.weather.ui.biz.test;

import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import netease.com.weather.R;
import netease.com.weather.ui.base.BaseFragment;

/**
 * Created by liu_shuai on 2016/11/20.
 */

public class TestFragment extends BaseFragment {
    @BindView(R.id.content)
    TextView content;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_test;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String url = "http://m.newsmth.net/img/ubb/ema/2.gif";
        String tempUrl = "<img src=\"" + url + "\" />";
        SpannableString spannableString = new SpannableString(tempUrl);

    }
}
