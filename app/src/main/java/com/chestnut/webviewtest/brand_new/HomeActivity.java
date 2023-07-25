package com.chestnut.webviewtest.brand_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chestnut.webviewtest.MainActivity;
import com.chestnut.webviewtest.R;
import com.chestnut.webviewtest.WebViewActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.wv_btn_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WebViewContainerActivity.class);
                intent.putExtra(WebViewContainerActivity.LOADED_URL, "https://m.jd.com/");
                startActivity(intent);
            }
        });

        findViewById(R.id.wv_btn_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WebViewContainerActivity.class);
                intent.putExtra(WebViewContainerActivity.LOADED_URL, "www.baidu.com");
                startActivity(intent);
            }
        });

        findViewById(R.id.wv_btn_three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WebViewContainerActivity.class);
                intent.putExtra(WebViewContainerActivity.LOADED_URL, "https://ai.m.taobao.com/?pid=mm_117626150_15986938_60582361");
                startActivity(intent);
            }
        });
    }
}