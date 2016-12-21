package netease.com.weather.ui.biz.web;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import netease.com.weather.R;
import netease.com.weather.ui.base.BaseFragment;

/**
 * Created by liu_shuai on 2016/12/21.
 */

public class WebViewFragment extends BaseFragment {
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.progress_bar)
    MaterialProgressBar mProgressBar;

    private String mUrl;
    public final static String PARAM_WEB_URL = "web_url";

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_web;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(PARAM_WEB_URL);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWebView();
        mWebView.loadUrl(mUrl);
    }


    private void initWebView() {
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                mProgressBar.setProgress(i);
            }

        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                return false;
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                //mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
            }
        });

        mProgressBar.setMax(100);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
