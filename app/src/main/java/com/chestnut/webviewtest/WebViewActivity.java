package com.chestnut.webviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chestnut.webviewtest.databinding.ActivityWebViewBinding;

public class WebViewActivity extends AppCompatActivity {

    public static final String KEY_URL = "KEY_URL";

    ActivityWebViewBinding mBinding;

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        webView = mBinding.wb;

        initWebView();
    }

    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        Intent intent = getIntent();
        String url = intent.getStringExtra(KEY_URL);
        webView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        webView.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        webView.resumeTimers();
    }

    @Override
    protected void onStop() {
        super.onStop();
        webView.pauseTimers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.getRoot().removeView(webView);
        webView.destroy();
    }
}