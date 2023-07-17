package com.chestnut.webviewtest.brand_new.event;

import android.app.Activity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.chestnut.webviewtest.R;

public class WidgetClickEvent {
    private static ImageView wvIvInterrupt;

    public static void setEvent(Activity activity, WebView webView)  {
        wvIvInterrupt = activity.findViewById(R.id.wv_iv_load);

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

        activity.findViewById(R.id.wv_iv_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isLoaded = (boolean) wvIvInterrupt.getTag();
                if (isLoaded) {
                    webView.reload();
                } else {
                    webView.stopLoading();
                }
            }
        });
    }
}
