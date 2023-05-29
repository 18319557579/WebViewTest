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

    @Override
    public void onResume() {
        super.onResume();
        //需要调用webview.onResume()，调用后这里会发生回调，又可以滑动起来了，并且在onPause()情况下进行的滑动
        //此时会一并反映出来
        Log.d("Daisy", "webview回调onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        //需要调用webview.onPause()，调用后这里会发生回调，webview会出现无法滑动（baidu）的情况
        Log.d("Daisy", "webview回调onPause");
    }
}
