package com.chestnut.webviewtest.brand_new.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chestnut.webviewtest.brand_new.tools.LogTool;

public class FragmentCallback extends Fragment {
    private String getSimpleName() {
        return getClass().getSimpleName();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        LogTool.v(getSimpleName() + " 回调 onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogTool.v(getSimpleName() + " 回调 onCreate");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogTool.v(getSimpleName() + " 回调 onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogTool.v(getSimpleName() + " 回调 onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogTool.v(getSimpleName() + " 回调 onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogTool.v(getSimpleName() + " 回调 onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogTool.v(getSimpleName() + " 回调 onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogTool.v(getSimpleName() + " 回调 onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogTool.v(getSimpleName() + " 回调 onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogTool.v(getSimpleName() + " 回调 onDetach");
    }
}
