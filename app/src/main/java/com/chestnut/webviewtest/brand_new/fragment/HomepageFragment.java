package com.chestnut.webviewtest.brand_new.fragment;

import static android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chestnut.webviewtest.MainActivity;
import com.chestnut.webviewtest.R;
import com.chestnut.webviewtest.brand_new.HolderActivity;
import com.chestnut.webviewtest.brand_new.HomeActivity;
import com.chestnut.webviewtest.brand_new.WebViewContainerActivity;
import com.chestnut.webviewtest.brand_new.tools.LogTool;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HomepageFragment extends FragmentCallback {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.wv_iv_start_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edt = view.findViewById(R.id.wv_et_search);
                String searchContent = edt.getText().toString();

                /*HolderActivity holderActivity = (HolderActivity) getActivity();
                try {
                    holderActivity.jumpWebViewWithUrl("http://www.baidu.com/s?&ie=utf-8&oe=UTF-8&wd=" + URLEncoder.encode(searchContent,"UTF-8"));
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }*/

                HolderActivity holderActivity = (HolderActivity) getActivity();
                holderActivity.jumpWebViewWithUrl(searchContent);
            }
        });

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

        view.findViewById(R.id.wv_btn_four).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HolderActivity holderActivity = (HolderActivity) getActivity();
                holderActivity.jumpWebViewWithUrl("https://www.xiaohongshu.com/activity/sem/walle?groupid=60f954717ae4040001eabf05&keywordid=295091738519&ad_id=51246368408&segmentid=0");
            }
        });
    }


}
