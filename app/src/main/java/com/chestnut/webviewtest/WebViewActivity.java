package com.chestnut.webviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.chestnut.webviewtest.databinding.ActivityWebViewBinding;

public class WebViewActivity extends AppCompatActivity {

    public static final String KEY_URL = "KEY_URL";

    ActivityWebViewBinding mBinding;

    WebView webView;

    SettingsManager settingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());


        webView = mBinding.wb;

        initView();
        initWebView();

        settingsManager = new SettingsManager(webView);
        settingsManager.setSettings();
    }

    private void initView() {
        mBinding.btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.getSettings().setLoadsImagesAutomatically(true);

            }
        });

        mBinding.btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.clearHistory();
            }
        });

        mBinding.btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.clearFormData();
            }
        });

        mBinding.btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ttt = webView.canGoBack();
                Log.d("Daisy", "是否可以后退：" + ttt);

                webView.goBackOrForward(-1);
            }
        });

        mBinding.btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.goBackOrForward(1);
            }
        });

    }

    private void initWebView() {
//        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        Intent intent = getIntent();
        String url = intent.getStringExtra(KEY_URL);
        webView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
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