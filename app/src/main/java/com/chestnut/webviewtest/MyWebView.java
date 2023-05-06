package com.chestnut.webviewtest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chestnut.webviewtest.utils.LogUtil;

public class MyWebView extends WebView {
    public MyWebView(@NonNull Context context) {
        super(context);
    }

    public MyWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
