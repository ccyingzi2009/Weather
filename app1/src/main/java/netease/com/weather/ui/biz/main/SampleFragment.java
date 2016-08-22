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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import netease.com.weather.R;
import netease.com.weather.data.model.MainSlider;
import netease.com.weather.ui.base.BaseActivity;
import netease.com.weather.ui.base.BaseLoadFragment;
import netease.com.weather.ui.base.constants.Constants;
import netease.com.weather.util.html.MainPageHandler;
import netease.com.weather.util.request.BaseRequest;
import netease.com.weather.util.request.HtmlRequest;

/**
 *
 */
public class SampleFragment extends BaseLoadFragment<List<MainSlider>> {
    private static final String ARG_TEXT = "ARG_TEXT";
    @Bind(R.id.recycle_view)
    RecyclerView mRecycleView;
    @Bind(R.id.toolbar)
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
            mListAdapter = new MainListAdapter((BaseActivity) getContext(), mLists);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((BaseActivity)getActivity()).setSupportActionBar(mToolbar);
        ActionBar ab = ((BaseActivity)getActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        loadNet();
    }

    @Override
    protected BaseRequest<List<MainSlider>> onCreateNet() {
        String url = Constants.MAIN_URL;
        return new HtmlRequest<>(url, new MainPageHandler(), this, this);
    }

    @Override
    public void onResponse(List<MainSlider> response) {
        super.onResponse(response);
        if (response != null && !response.isEmpty()) {
            mLists.clear();
            mLists.addAll(response);
            mRecycleView.setAdapter(mListAdapter);
            final StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration(mListAdapter);
            mRecycleView.addItemDecoration(decoration);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
