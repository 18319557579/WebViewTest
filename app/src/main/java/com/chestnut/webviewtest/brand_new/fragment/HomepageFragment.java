package com.chestnut.webviewtest.brand_new.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chestnut.webviewtest.MainActivity;
import com.chestnut.webviewtest.R;
import com.chestnut.webviewtest.brand_new.HolderActivity;
import com.chestnut.webviewtest.brand_new.HomeActivity;
import com.chestnut.webviewtest.brand_new.WebViewContainerActivity;
import com.chestnut.webviewtest.brand_new.tools.LogTool;

public class HomepageFragment extends FragmentCallback {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.wv_btn_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HolderActivity holderActivity = (HolderActivity) getActivity();
                holderActivity.jumpWebViewWithUrl("https://m.jd.com/");
            }
        });

        view.findViewById(R.id.wv_btn_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HolderActivity holderActivity = (HolderActivity) getActivity();
                holderActivity.jumpWebViewWithUrl("www.baidu.com");
            }
        });

        view.findViewById(R.id.wv_btn_three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HolderActivity holderActivity = (HolderActivity) getActivity();
                holderActivity.jumpWebViewWithUrl("https://ai.m.taobao.com/?pid=mm_117626150_15986938_60582361");
            }
        });
    }


}
