package com.chestnut.webviewtest.brand_new.event;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;

import com.chestnut.webviewtest.R;

public class WidgetClickEvent {
    public static void setEvent(Activity activity, WebView webView)  {
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

        activity.findViewById(R.id.wb_iv_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });

        activity.findViewById(R.id.wb_iv_interrupt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.stopLoading();
            }
        });
    }
}
