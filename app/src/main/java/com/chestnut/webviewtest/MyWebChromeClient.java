package com.chestnut.webviewtest;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.chestnut.webviewtest.utils.LogUtil;

public abstract class MyWebChromeClient extends WebChromeClient {
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        String progress = newProgress + "%";
        LogUtil.d("进度发生改变：" + progress);
        innerProgressChanged(progress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        innerReceivedTitle(title);
    }

    abstract public void innerProgressChanged(String newProgress);

    abstract public void innerReceivedTitle(String title);
}
