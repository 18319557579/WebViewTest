package com.chestnut.webviewtest.brand_new.tools;

import android.util.Log;

public class LogTool {
    private static final String TAG = "Daisy";

    //判断是否能输入日志
    private static boolean canPrint() {
        return true;
    }

    public static void v(String msg) {
        LogTool.v(TAG, msg);
    }

    public static void d(String msg) {
        LogTool.d(TAG, msg);
    }

    public static void e(String msg) {
        LogTool.e(TAG, msg);
    }

//----------------------------------------------------------------
    public static void v(String tag, String msg) {
        if (canPrint()) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (canPrint()) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (canPrint()) {
            Log.e(tag, msg);
        }
    }
}
