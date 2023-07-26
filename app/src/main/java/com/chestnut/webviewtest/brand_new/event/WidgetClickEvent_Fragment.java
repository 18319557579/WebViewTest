package com.chestnut.webviewtest.brand_new.event;

import android.app.Activity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.chestnut.webviewtest.R;
import com.chestnut.webviewtest.brand_new.tools.LogTool;

public class WidgetClickEvent_Fragment {

    public static void setEvent(View view, WebView webView)  {
        ImageView wvIvLoad = view.findViewById(R.id.wv_iv_load);

        wvIvLoad.setOnClickListener(new View.OnClickListener() {
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
    }
}
