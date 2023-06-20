package com.chestnut.webviewtest;

import android.webkit.WebSettings;
import android.webkit.WebView;

public class SettingsManager {
    WebView webView;
    WebSettings webSettings;

    public SettingsManager(WebView wb) {
        webView = wb;
        webSettings = webView.getSettings();
    }

    public void setSettings() {
        webSettings.setJavaScriptEnabled(true);

//        webSettings.setPluginState();

        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);

        //要下面这两个同时设置了true，才能实现缩放。上面的是下面的前提
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);

        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件


        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        webSettings.setAllowFileAccess(true);

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webSettings.setLoadsImagesAutomatically(true);

        //卧槽，设置为false后，图片都不会加载了。
        webSettings.setLoadsImagesAutomatically(true);

        webSettings.setDefaultTextEncodingName("UTF-8");
    }
}
