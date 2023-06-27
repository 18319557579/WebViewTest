package com.chestnut.webviewtest.click;

import android.util.Log;
import android.view.View;
import android.webkit.WebView;

public class LongClickCustom implements View.OnLongClickListener {

    @Override
    public boolean onLongClick(View v) {
        WebView wb = (WebView) v;
        WebView.HitTestResult result = wb.getHitTestResult();
        if (result != null) {
            switch (result.getType()) {
                case WebView.HitTestResult.IMAGE_TYPE:
                case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                    String imgUrl = result.getExtra();
                    Log.d("Daisy", "获取到图片的链接：" + imgUrl);
                    return true;
            }
        }
        return false;
    }
}
