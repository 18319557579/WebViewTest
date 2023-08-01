package com.chestnut.webviewtest.brand_new;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebBackForwardList;

import com.chestnut.webviewtest.R;
import com.chestnut.webviewtest.brand_new.fragment.HomepageFragment;
import com.chestnut.webviewtest.brand_new.fragment.WebViewFragment;
import com.chestnut.webviewtest.brand_new.tools.LogTool;

import java.util.List;

public class HolderActivity extends AppCompatActivity {

    private HomepageFragment mHomepageFragment;
    private WebViewFragment mWebViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holder);

        mHomepageFragment = new HomepageFragment();
        mWebViewFragment = new WebViewFragment();

        addOrSwitchFragment(mHomepageFragment);

        initView();
    }

    private void addOrSwitchFragment(Fragment fragmentToSwitch) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //获取当前的Fragment
        Fragment currentFragment = getCurrentFragment();
        LogTool.d("当前的fragment：" + currentFragment);
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }

        if (fragmentToSwitch.isAdded()) {
            transaction.show(fragmentToSwitch);
        } else {
            transaction.add(R.id.wv_rl_holder, fragmentToSwitch);
        }

        transaction.commit();
    }

    private void initView() {
        findViewById(R.id.wv_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment currentFragment = getCurrentFragment();
                if (currentFragment instanceof WebViewFragment) {
                    WebViewFragment webViewFragment = (WebViewFragment) currentFragment;
                    //如果还能网页还能后退，那么后退
                    if (webViewFragment.mWebView.canGoBack()) {
                        webViewFragment.mWebView.goBack();
                    } else {
                        addOrSwitchFragment(mHomepageFragment);
                    }
                }
            }
        });

        findViewById(R.id.wv_iv_go_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrSwitchFragment(mHomepageFragment);
            }
        });

        findViewById(R.id.wv_iv_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment currentFragment = getCurrentFragment();
                if (currentFragment instanceof WebViewFragment) {
                    WebViewFragment webViewFragment = (WebViewFragment) currentFragment;
                    String url = webViewFragment.mWebView.getUrl();

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, url);
                    startActivity(Intent.createChooser(intent ,"分享"));
                }



            }
        });

        findViewById(R.id.wv_iv_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment currentFragment = getCurrentFragment();
                if (currentFragment instanceof WebViewFragment) {
                    WebViewFragment webViewFragment = (WebViewFragment) currentFragment;
                    WebBackForwardList webBackForwardList = webViewFragment.mWebView.copyBackForwardList();
                    LogTool.d("" + webBackForwardList.getCurrentIndex() + ", " + webBackForwardList.getCurrentItem().getUrl());
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            LogTool.d("点击了返回按钮");

            Fragment currentFragment = getCurrentFragment();
            if (currentFragment instanceof WebViewFragment) {
                WebViewFragment webViewFragment = (WebViewFragment) currentFragment;
                //如果还能网页还能后退，那么后退
                if (webViewFragment.mWebView.canGoBack()) {
                    webViewFragment.mWebView.goBack();
                } else {
                    addOrSwitchFragment(mHomepageFragment);
                }
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void jumpWebViewWithUrl(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(WebViewFragment.LOADED_URL, url);
        mWebViewFragment.setArguments(bundle);

        addOrSwitchFragment(mWebViewFragment);
    }

    private Fragment getCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        List<Fragment> fragments = fragmentManager.getFragments();
        for(Fragment fragment : fragments){
            if(fragment != null && fragment.isVisible()) {
                return fragment;
            }
        }

        return null;
    }
}