package com.chestnut.webviewtest.brand_new.expand;

import android.app.Activity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.chestnut.webviewtest.R;
import com.chestnut.webviewtest.brand_new.tools.LogTool;

public class CustomWebChromeClient extends WebChromeClient implements LifecycleEventObserver {
    private final AppCompatActivity mActivity;
    private ProgressBar mProgressBar;
    private TextView wvTvTitle;

    public CustomWebChromeClient(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (event.equals(Lifecycle.Event.ON_CREATE)) {
            LogTool.d("回调了onCreate");
            LogTool.d("当前的状态：" + mActivity.getLifecycle().getCurrentState());
            mProgressBar = mActivity.findViewById(R.id.wv_pb_progress);
            wvTvTitle = mActivity.findViewById(R.id.wv_tv_title);
        }
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
//        LogTool.d("进度发生改变：" + newProgress);

        mProgressBar.setProgress(newProgress);

        if (newProgress == 100) {
            mProgressBar.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        LogTool.d("回调了标题：" + title);
        wvTvTitle.setText(title);
    }
}
