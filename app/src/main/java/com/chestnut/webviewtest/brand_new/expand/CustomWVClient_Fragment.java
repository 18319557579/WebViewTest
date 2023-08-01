package com.chestnut.webviewtest.brand_new.expand;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.chestnut.webviewtest.R;
import com.chestnut.webviewtest.brand_new.fragment.FragmentListener;
import com.chestnut.webviewtest.brand_new.tools.ContextTool;
import com.chestnut.webviewtest.brand_new.tools.LogTool;

import java.util.List;

public class CustomWVClient_Fragment extends WebViewClient implements FragmentListener {
    private TextView wvTvTitle;

    public CustomWVClient_Fragment() {
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String requestUrl = request.getUrl().toString();
        LogTool.i("request.getUrl().toString(): " + requestUrl);
        LogTool.i("view.getUrl(): " + view.getUrl());
        LogTool.i("view.getOriginalUrl(): " + view.getOriginalUrl());

        if (requestUrl.startsWith("http://") || requestUrl.startsWith("https://")) {
            return false;
        }

        try {
            Uri myUri = Uri.parse(requestUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW, myUri);

            PackageManager mPackageManager = ContextTool.getCurrentActivity().getPackageManager();

//            List<ResolveInfo> mResolveInfos = mPackageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//            ResolveInfo info = mPackageManager.resolveActivity(intent, PackageManager.MATCH_ALL);

            ComponentName componentName = intent.resolveActivity(mPackageManager);
            String packageName = componentName.getPackageName();
            ApplicationInfo applicationInfo = mPackageManager.getApplicationInfo(packageName, 0);
            String applicationName = (String) mPackageManager.getApplicationLabel(applicationInfo);

            LogTool.d("应用名：" + applicationName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;

//        WebView.HitTestResult hit = view.getHitTestResult();
//        //hit.getExtra()为null或者hit.getType() == 0都表示即将加载的URL会发生重定向，需要做拦截处理
//        if (TextUtils.isEmpty(hit.getExtra()) || hit.getType() == 0) {
//            //通过判断开头协议就可解决大部分重定向问题了，有另外的需求可以在此判断下操作
//            LogTool.e("Daisy", "重定向: " + hit.getType() + " && EXTRA（）" + hit.getExtra() + "------");
//            LogTool.e("Daisy", "GetURL: " + view.getUrl() + "\n" +"getOriginalUrl()"+ view.getOriginalUrl());
//            LogTool.d("Daisy", "URL: " + url);
//        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        LogTool.d("出现了SSL错误：" + error.toString());
        handler.proceed();
    }

    /*
    发现canGoBack()和canGoForward()在onPageStarted()回调后准确
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        LogTool.i("页面回调onPageStarted: " + url);

        LogTool.d("222 是否可以回退：" + view.canGoBack());
        LogTool.d("222 是否可以前进：" + view.canGoForward());


    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        LogTool.i("页面回调onPageFinished: " + url);

        LogTool.d("333 是否可以回退：" + view.canGoBack());
        LogTool.d("333 是否可以前进：" + view.canGoForward());


    }

    @Override
    public void onViewCreated(View view) {
        wvTvTitle = view.findViewById(R.id.wv_tv_title);
    }
}
