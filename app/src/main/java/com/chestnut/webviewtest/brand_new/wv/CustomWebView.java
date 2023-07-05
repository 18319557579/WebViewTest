package com.chestnut.webviewtest.brand_new.wv;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chestnut.webviewtest.brand_new.tools.LogTool;

public class CustomWebView extends WebView {
    public CustomWebView(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogTool.d("CustomWebView回调了onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogTool.d("CustomWebView回调了onPause()");
    }
}
