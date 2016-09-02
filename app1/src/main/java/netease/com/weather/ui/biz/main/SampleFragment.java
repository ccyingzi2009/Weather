/*
 * BottomBar library for Android
 * Copyright (c) 2016 Iiro Krankka (http://github.com/roughike).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package netease.com.weather.ui.biz.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import netease.com.weather.R;
import netease.com.weather.data.model.MainBean;
import netease.com.weather.data.model.MainSlider;
import netease.com.weather.ui.MainActivity;
import netease.com.weather.ui.base.BaseActivity;
import netease.com.weather.ui.base.BaseLoadFragment;
import netease.com.weather.ui.base.constants.Constants;
import netease.com.weather.util.JsonUtils;
import netease.com.weather.util.PrefHelper;
import netease.com.weather.util.html.MainPageHandler;
import netease.com.weather.util.request.BaseRequest;
import netease.com.weather.util.request.HtmlRequest;

/**
 *
 */
public class SampleFragment extends BaseLoadFragment<MainBean> {
    private static final String ARG_TEXT = "ARG_TEXT";
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private MainListAdapter mListAdapter;
    private List<MainSlider> mLists = new ArrayList<>();


    public SampleFragment() {
    }

    public static SampleFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);

        SampleFragment sampleFragment = new SampleFragment();
        sampleFragment.setArguments(args);

        return sampleFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mListAdapter == null) {
            mListAdapter = new MainListAdapter((MainActivity) getContext(), mLists);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((BaseActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar ab = ((BaseActivity) getActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        loadNet();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_main_list;
    }

    @Override
    protected BaseRequest<MainBean> onCreateNet(RefreshMode mode) {
        String url = Constants.MAIN_URL;
        return new HtmlRequest<>(url, new MainPageHandler(), true);
    }

    @Override
    public void onNetResponse(RefreshMode mode, MainBean response) {
        if (mRecycleView == null) {
            return;
        }
        super.onNetResponse(mode, response);
        if (response != null) {
            List<MainSlider> sliders = response.getSliders();
            if (sliders != null && !sliders.isEmpty()) {
                mLists.clear();
                mLists.addAll(sliders);
                mRecycleView.setAdapter(mListAdapter);
                final StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration(mListAdapter);
                mRecycleView.addItemDecoration(decoration);
            }
        }
    }

    @Override
    protected MainBean getLocalData() {
        String mainStr = PrefHelper.getString(Constants.MAIN_URL, "");
        if (!TextUtils.isEmpty(mainStr)) {
            return JsonUtils.fromJson(mainStr, new TypeToken<MainBean>() {});
        }
        return super.getLocalData();
    }

    @Override
    protected void onTaskStateChange(TaskState state) {
        if (state == TaskState.prepare) {
            if (isContentEmpty()) {
                setViewVisible(mRecycleView, View.GONE);
            } else {
                setViewVisible(mRecycleView, View.VISIBLE);
            }
        } else if (state == TaskState.success) {
            setViewVisible(mRecycleView, View.VISIBLE);
        } else if (state == TaskState.failed) {
            if (isContentEmpty()) {
                setViewVisible(mRecycleView, View.GONE);
            } else {
                setViewVisible(mRecycleView, View.VISIBLE);
            }
        }
        super.onTaskStateChange(state);
    }
}
