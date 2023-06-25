package com.chestnut.webviewtest.map;

import android.webkit.JavascriptInterface;

import com.chestnut.webviewtest.utils.LogUtil;

public class AndroidToJs {
    @JavascriptInterface
    public String hello(String msg) {
        LogUtil.d("js调用了hello：" + msg);
        return "567";
    }
}
