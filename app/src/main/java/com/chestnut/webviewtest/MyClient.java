package com.chestnut.webviewtest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chestnut.webviewtest.utils.LogUtil;

import java.util.Set;

public class MyClient extends WebViewClient {

    Activity mActivity;

    public MyClient(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();

        WebView.HitTestResult hit = view.getHitTestResult();
        //hit.getExtra()为null或者hit.getType() == 0都表示即将加载的URL会发生重定向，需要做拦截处理
        if (TextUtils.isEmpty(hit.getExtra()) || hit.getType() == 0) {
            //通过判断开头协议就可解决大部分重定向问题了，有另外的需求可以在此判断下操作
            Log.e("Daisy", "重定向: " + hit.getType() + " && EXTRA（）" + hit.getExtra() + "------");
            Log.e("Daisy", "GetURL: " + view.getUrl() + "\n" +"getOriginalUrl()"+ view.getOriginalUrl());
            Log.d("Daisy", "URL: " + url);
        }

        if (url.startsWith("http://") || url.startsWith("https://")) { //加载的url是http/https协议地址
            view.loadUrl(url);
            return false; //返回false表示此url默认由系统处理,url未加载完成，会继续往下走

        } else { //加载的url是自定义协议地址
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                mActivity.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return super.shouldOverrideUrlLoading(view, url);
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
