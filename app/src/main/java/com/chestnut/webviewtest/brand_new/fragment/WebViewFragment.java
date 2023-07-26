package com.chestnut.webviewtest.brand_new.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chestnut.webviewtest.R;
import com.chestnut.webviewtest.brand_new.event.WVLongClickEvent;
import com.chestnut.webviewtest.brand_new.event.WidgetClickEvent;
import com.chestnut.webviewtest.brand_new.event.WidgetClickEvent_Fragment;
import com.chestnut.webviewtest.brand_new.expand.CustomWVClient;
import com.chestnut.webviewtest.brand_new.expand.CustomWVClient_Fragment;
import com.chestnut.webviewtest.brand_new.expand.CustomWebChromeClient;
import com.chestnut.webviewtest.brand_new.expand.CustomWebChromeClient_Fragment;
import com.chestnut.webviewtest.brand_new.expand.WVSettings;
import com.chestnut.webviewtest.brand_new.tools.LogTool;
import com.chestnut.webviewtest.brand_new.wv.CustomWebView;

public class WebViewFragment extends Fragment {

    public static final String LOADED_URL = "WEB_VIEW_FRAGMENT_LOADED_URL";

    CustomWebChromeClient_Fragment customWebChromeClient;
    CustomWVClient_Fragment customWVClientFragment;

    private CustomWebView mWebView;

    public WebViewFragment() {
        super(R.layout.fragment_web_view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogTool.d("WebViewFragment 回调 onCreate");

        mWebView = createWebView();

        WVSettings.setSettings(mWebView);

        customWVClientFragment = new CustomWVClient_Fragment();
        mWebView.setWebViewClient(customWVClientFragment);

        customWebChromeClient = new CustomWebChromeClient_Fragment(this);
        mWebView.setWebChromeClient(customWebChromeClient);

        mWebView.setOnLongClickListener(new WVLongClickEvent());
    }

    private CustomWebView createWebView() {
        return new CustomWebView(getActivity().getApplicationContext());
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogTool.d("WebViewFragment 回调 onViewCreated");

        //代码生成WebView
        generateWebView(view, mWebView);
        customWebChromeClient.onViewCreated(view);
        customWVClientFragment.onViewCreated(view);


        //设置点击事件
        WidgetClickEvent_Fragment.setEvent(view, mWebView);

        loadParams();
    }

    private void loadParams() {
        mWebView.loadUrl(getArguments().getString(WebViewFragment.LOADED_URL));
    }

    private void generateWebView(View view, WebView webView) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(params);
        FrameLayout wvRlContainer = view.findViewById(R.id.wv_rl_container);
        wvRlContainer.addView(webView);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogTool.d("WebViewFragment 回调 onResume");

        mWebView.resumeTimers();
        mWebView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LogTool.d("WebViewFragment 回调 onPause");

        mWebView.onPause();
        mWebView.pauseTimers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogTool.d("WebViewFragment 回调 onDestroy");

        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}
