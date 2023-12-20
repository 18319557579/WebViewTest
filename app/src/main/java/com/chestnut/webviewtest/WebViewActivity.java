package com.chestnut.webviewtest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.chestnut.webviewtest.click.LongClickCustom;
import com.chestnut.webviewtest.databinding.ActivityWebViewBinding;
import com.chestnut.webviewtest.map.AndroidToJs;
import com.chestnut.webviewtest.utils.LogUtil;

import java.util.HashMap;
import java.util.Set;

public class WebViewActivity extends AppCompatActivity {

    public static final String KEY_URL = "KEY_URL";

    ActivityWebViewBinding mBinding;

    MyWebView webView;

    SettingsManager settingsManager;

    //原来是可以在变量这里实现某个类，并重写方法的
    MyWebChromeClient mwcc = new MyWebChromeClient() {
        @Override
        public void innerProgressChanged(int newProgress) {
            mBinding.tvProgress.setText(newProgress + "%");
            mBinding.pbProgress.setProgress(newProgress);

            if (newProgress == 100) {
                mBinding.pbProgress.setVisibility(View.INVISIBLE);
            } else {
                mBinding.pbProgress.setVisibility(View.VISIBLE);
            }
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

        @Override
        public boolean innerJsConfirm(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            Uri uri = Uri.parse(message);
            // 如果url的协议 = 预先约定的 js 协议
            // 就解析往下解析参数
            if ( uri.getScheme().equals("js")) {

                // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                // 所以拦截url,下面JS开始调用Android需要的方法
                if (uri.getAuthority().equals("webview")) {

                    //
                    // 执行JS所需要调用的逻辑
                    LogUtil.d("js利用prompt调用了java的方法");
                    // 可以在协议上带有参数并传递到Android上
                    HashMap<String, String> params = new HashMap<>();
                    Set<String> collection = uri.getQueryParameterNames();
                    for (String paramKey : collection) {
                        LogUtil.d("参数: " + paramKey + ", 参数值: " + uri.getQueryParameter(paramKey));
                    }

                    //参数result:代表消息框的返回值(输入值)
                    result.confirm("js调用了Android的方法成功啦");
                }
                return true;
            }
            return false;
        }


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new MyWebView(getApplicationContext());
        webView.setLayoutParams(params);
        mBinding.rlContainer.addView(webView);

        initView();
        initWebView();

        settingsManager = new SettingsManager(webView);
        settingsManager.setSettings();

        webView.setWebViewClient(new MyClient(this));
        webView.setWebChromeClient(mwcc);

    }

    private void initView() {
        mBinding.btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.evaluateJavascript("javascript:callJS('hello js')", new ValueCallback<String>() {
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
                String currentUrl =  webView.getUrl();
                Log.d("Daisy", "当前页面的URL：" + currentUrl);

                webView.setScrollY(0);
            }
        });

        mBinding.btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });

        mBinding.btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.stopLoading();
            }
        });

    }

    private void initWebView() {
        webView.setWebViewClient(new WebViewClient());

        webView.addJavascriptInterface(new AndroidToJs(), "test");

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        webView.setOnLongClickListener(new LongClickCustom());

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
        webView.resumeTimers();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
        webView.pauseTimers();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        webView.resumeTimers();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        webView.pauseTimers();
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