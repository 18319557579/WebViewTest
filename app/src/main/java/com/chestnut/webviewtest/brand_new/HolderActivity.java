package com.chestnut.webviewtest.brand_new;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

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
        Fragment currentFragment = null;
        List<Fragment> fragments = fragmentManager.getFragments();
        for(Fragment fragment : fragments){
            if(fragment != null && fragment.isVisible()) {
                currentFragment = fragment;
                break;
            }
        }

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
        findViewById(R.id.wv_iv_go_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrSwitchFragment(mHomepageFragment);
            }
        });

        findViewById(R.id.wv_iv_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString(WebViewFragment.LOADED_URL, "https://m.jd.com/");
                mWebViewFragment.setArguments(bundle);

                addOrSwitchFragment(mWebViewFragment);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            LogTool.d("点击了返回按钮");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}