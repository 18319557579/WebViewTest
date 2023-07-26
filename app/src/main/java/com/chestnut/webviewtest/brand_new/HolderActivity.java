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

public class HolderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holder);

        replaceFragment(new HomepageFragment());

        setView();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.wv_rl_holder, fragment);
        transaction.commit();
    }

    private void setView() {
        findViewById(R.id.wv_iv_go_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewFragment webViewFragment = new WebViewFragment();

                Bundle bundle = new Bundle();
                bundle.putString(WebViewFragment.LOADED_URL, "https://m.jd.com/");
                webViewFragment.setArguments(bundle);

                replaceFragment(webViewFragment);
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