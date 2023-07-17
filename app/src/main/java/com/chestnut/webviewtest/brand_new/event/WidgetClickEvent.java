package com.chestnut.webviewtest.brand_new.event;

import android.app.Activity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.chestnut.webviewtest.R;
import com.chestnut.webviewtest.brand_new.tools.LogTool;

public class WidgetClickEvent {
    private static ImageView wvIvLoad;

    public static void setEvent(Activity activity, WebView webView)  {
        wvIvLoad = activity.findViewById(R.id.wv_iv_load);

        activity.findViewById(R.id.wv_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    activity.finish();
                }
            }
        });

        activity.findViewById(R.id.wv_iv_forward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoForward()) {
                    webView.goForward();
                }
            }
        });

        activity.findViewById(R.id.wv_iv_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isLoaded = (boolean) wvIvLoad.getTag();
                if (isLoaded) {
                    webView.reload();
                } else {
                    webView.stopLoading();
                }
            }
        });

        activity.findViewById(R.id.iv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogTool.d("是否可以回退：" + webView.canGoBack());
                LogTool.d("是否可以前进：" + webView.canGoForward());
            }
        });
    }
}
