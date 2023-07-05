package com.chestnut.webviewtest.brand_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.chestnut.webviewtest.R;
import com.chestnut.webviewtest.brand_new.event.WVLongClickEvent;
import com.chestnut.webviewtest.brand_new.event.WidgetClickEvent;
import com.chestnut.webviewtest.brand_new.expand.CustomWVClient;
import com.chestnut.webviewtest.brand_new.expand.CustomWebChromeClient;
import com.chestnut.webviewtest.brand_new.expand.WVSettings;
import com.chestnut.webviewtest.brand_new.wv.CustomWebView;

public class WebViewContainerActivity extends AppCompatActivity {
    public static final String LOADED_URL = "LOADED_URL";

    private CustomWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_container);

        CustomWebChromeClient customWebChromeClient = new CustomWebChromeClient(this);
        getLifecycle().addObserver(customWebChromeClient);

        //要先把WebView创建出来
        generateWebView();

        WidgetClickEvent.setEvent(this, mWebView);

        //将WebSettings的功能分离到一个类中
        WVSettings.setSettings(mWebView);
        mWebView.setWebViewClient(new CustomWVClient(this));
        mWebView.setWebChromeClient(customWebChromeClient);
        mWebView.setOnLongClickListener(new WVLongClickEvent());

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        loadParams();
//                    }
//                });
//            }
//        }).start();
        loadParams();
    }

    //创建WebView
    private void generateWebView() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new CustomWebView(getApplicationContext());
        mWebView.setLayoutParams(params);
        FrameLayout wvRlContainer = findViewById(R.id.wv_rl_container);
        wvRlContainer.addView(mWebView);
    }

    //加载Url
    private void loadParams() {
        Intent intent = getIntent();
        String url = intent.getStringExtra(LOADED_URL);
        mWebView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.resumeTimers();
        mWebView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
        mWebView.pauseTimers();
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}