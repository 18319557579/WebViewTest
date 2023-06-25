package com.chestnut.webviewtest;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chestnut.webviewtest.utils.LogUtil;

import java.util.Set;

public class MyClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        Uri uri = Uri.parse(url);
        if (uri.getScheme().equals("js")) {
            if (uri.getAuthority().equals("webview")) {
                LogUtil.d("js调用了android的方法");
                Set<String> collection =  uri.getQueryParameterNames();
                for (String sss : collection) {
                    LogUtil.d("参数依次是：" + sss);
                }

                String result = "世界你好";
                view.loadUrl("javascript:returnResult(\"" + result + "\")");

                return true;
            }
        }

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
        LogUtil.d("出现了SSL错误：" + error.toString());
        handler.proceed();
    }
}
