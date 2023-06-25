package com.chestnut.webviewtest;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chestnut.webviewtest.utils.LogUtil;

public class MyClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//        view.loadUrl(request.getUrl());
        LogUtil.d("加载的URL：" + request.getUrl().toString());
        view.loadUrl(request.getUrl().toString());
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        LogUtil.d("页面开始：" + url + ", favicon: " + favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        LogUtil.d("页面结束：" + url);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        Log.d("Daisy", "加载的资源：" + url);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//        super.onReceivedError(view, request, error);
        Log.d("Daisy", "错误码：" + error.getErrorCode() + ", 错误信息：" + error.getDescription()
            + ", 错误的url：" + request.getUrl().toString());

        if (error.getErrorCode() == -2) {
            view.loadUrl("file:///android_asset/index.html");
        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
    }
}
