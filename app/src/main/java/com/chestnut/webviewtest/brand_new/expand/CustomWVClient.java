package com.chestnut.webviewtest.brand_new.expand;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.chestnut.webviewtest.R;
import com.chestnut.webviewtest.brand_new.tools.LogTool;
import com.chestnut.webviewtest.utils.LogUtil;

public class CustomWVClient extends WebViewClient {
    private Activity mActivity;
    private TextView wvTvTitle;

    public CustomWVClient(Activity mActivity) {
        this.mActivity = mActivity;
        wvTvTitle = mActivity.findViewById(R.id.wv_tv_title);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        LogTool.d("加载的url:" + url);

        if (view.getUrl().startsWith("http://") || view.getUrl().startsWith("https://")) {
            //todo 和onReceivedTitle那里有冲突
            wvTvTitle.setText(view.getUrl());
        }

        WebView.HitTestResult hit = view.getHitTestResult();
        //hit.getExtra()为null或者hit.getType() == 0都表示即将加载的URL会发生重定向，需要做拦截处理
        if (TextUtils.isEmpty(hit.getExtra()) || hit.getType() == 0) {
            //通过判断开头协议就可解决大部分重定向问题了，有另外的需求可以在此判断下操作
            LogTool.e("Daisy", "重定向: " + hit.getType() + " && EXTRA（）" + hit.getExtra() + "------");
            LogTool.e("Daisy", "GetURL: " + view.getUrl() + "\n" +"getOriginalUrl()"+ view.getOriginalUrl());
            LogTool.d("Daisy", "URL: " + url);
        }

        if (url.startsWith("http://") || url.startsWith("https://")) { //加载的url是http/https协议地址
            view.loadUrl(url);
            //返回false表示此url默认由系统处理,url未加载完成，会继续往下走
            return false;

        } else { //加载的url是自定义协议地址
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                mActivity.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        LogTool.d("出现了SSL错误：" + error.toString());
        handler.proceed();
    }
}
