package netease.com.weather.ui.biz.custom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import netease.com.weather.R;
import netease.com.weather.ui.base.BaseFragment;

/**
 * Created by user on 16-4-20.
 */
public class CustomViewFragment extends BaseFragment {

    @Bind(R.id.send)
    Button mSend;

    public static CustomViewFragment newInstance() {
        Bundle args = new Bundle();
        CustomViewFragment top10Fragment = new CustomViewFragment();
        top10Fragment.setArguments(args);
        return top10Fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_view, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected int getContentViewId() {
        return 0;
    }

}
