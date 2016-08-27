package netease.com.weather.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by liu_shuai on 16/3/19.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getContentViewId() > 0) {
            View v = inflater.inflate(getContentViewId(), container, false);
            ButterKnife.bind(this, v);
            initLayout(v, inflater, container);
            return v;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract int getContentViewId();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected void initLayout(View v, LayoutInflater inflater, ViewGroup container) {

    }

    public ActionBar getActionBar(){
        if (getActivity() != null) {
            return ((BaseActivity)getActivity()).getSupportActionBar();
        }
        return null;
    }
}
