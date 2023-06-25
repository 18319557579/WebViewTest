package com.chestnut.webviewtest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chestnut.webviewtest.databinding.ActivityWebViewBinding;
import com.chestnut.webviewtest.utils.LogUtil;

public class WebViewActivity extends AppCompatActivity {

    public static final String KEY_URL = "KEY_URL";

    ActivityWebViewBinding mBinding;

    WebView webView;

    SettingsManager settingsManager;

    //原来是可以在变量这里实现某个类，并重写方法的
    MyWebChromeClient mwcc = new MyWebChromeClient() {
        @Override
        public void innerProgressChanged(String newProgress) {
            mBinding.tvProgress.setText(newProgress);
        }

        @Override
        public void innerReceivedTitle(String title) {
            mBinding.tvTitle.setText(title);
        }

        @Override
        public boolean innerJsAlert(WebView view, String url, String message, JsResult result) {
            AlertDialog.Builder b = new AlertDialog.Builder(WebViewActivity.this);
            b.setTitle("Alert");
            b.setMessage(message);
            b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            });
            b.setCancelable(false);
            b.create().show();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new WebView(getApplicationContext());
        webView.setLayoutParams(params);
        mBinding.rlContainer.addView(webView);

        initView();
        initWebView();

        settingsManager = new SettingsManager(webView);
        settingsManager.setSettings();

        webView.setWebViewClient(new MyClient());
        webView.setWebChromeClient(mwcc);

    }

    private void initView() {
        mBinding.btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        LogUtil.d("得到了返回结果：" + value);
                    }
                });

            }
        });

        mBinding.btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.post(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl("javascript:callJS()");
                    }
                });
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
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}