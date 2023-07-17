package com.chestnut.webviewtest.brand_new.expand;

import android.app.Activity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.bumptech.glide.Glide;
import com.chestnut.webviewtest.R;
import com.chestnut.webviewtest.brand_new.tools.LogTool;

public class CustomWebChromeClient extends WebChromeClient implements LifecycleEventObserver {
    private final AppCompatActivity mActivity;

    //加载的进度条
    private ProgressBar mProgressBar;

    //标题
    private TextView wvTvTitle;

    //刷新
//    private ImageView wvIvRefresh;

    //中断加载
    private ImageView wvIvInterrupt;

    public CustomWebChromeClient(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        //当WebViewContainerActivity回到onCreated
        if (event.equals(Lifecycle.Event.ON_CREATE)) {
            LogTool.d("回调了onCreate");
            LogTool.d("当前的状态：" + mActivity.getLifecycle().getCurrentState());
            mProgressBar = mActivity.findViewById(R.id.wv_pb_progress);
            wvTvTitle = mActivity.findViewById(R.id.wv_tv_title);

//            wvIvRefresh = mActivity.findViewById(R.id.wv_iv_refresh);
            wvIvInterrupt = mActivity.findViewById(R.id.wv_iv_interrupt);
        }
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        LogTool.d("进度发生改变：" + newProgress);

        mProgressBar.setProgress(newProgress);

        if (newProgress == 100) {
            mProgressBar.setVisibility(View.INVISIBLE);

            boolean isLoaded = (boolean) wvIvInterrupt.getTag();
            if (!isLoaded) {
                //设置已经加载完毕
                wvIvInterrupt.setTag(true);
                Glide.with(mActivity).load(R.drawable.refresh).into(wvIvInterrupt);
            }


        } else {
            mProgressBar.setVisibility(View.VISIBLE);

            /*这里的作用是，只有原本是已经加载完成了，才会去设置图片与tag，因为进度不到100%时，
            * 会重复回调多次这里，加上判断的话可以防止多次无效地执行下面的语句。上面那个if也
            * 进行了同样的操作，主要是为了逻辑上和这里对齐*/
            if (wvIvInterrupt.getTag() == null || (boolean) wvIvInterrupt.getTag()) {
                wvIvInterrupt.setTag(false);
                Glide.with(mActivity).load(R.drawable.wv_close).into(wvIvInterrupt);
            }

        }
    }

    //todo 标题这里的顺序会有点问题，这里的设置和shouldOverrideUrlLoading的设置顺序，目前还没有掌握
    @Override
    public void onReceivedTitle(WebView view, String title) {
        LogTool.d("回调了标题：" + title);
        wvTvTitle.setText(title);
    }
}
