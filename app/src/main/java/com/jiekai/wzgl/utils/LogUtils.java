package com.jiekai.wzgl.utils;

import android.util.Log;

import com.jiekai.wzgl.BuildConfig;

/**
 * Created by LaoWu on 2017/11/23.
 * 打印日志的工具类
 */

public class LogUtils {
    private static final boolean LOG_SWITCH = BuildConfig.DEBUG;
    private static final String LOG_TAG = "wzgl";

    public void i(String msg) {
        i(LOG_TAG, msg);
    }

    public void w(String msg) {
        w(LOG_TAG, msg);
    }

    public void e(String msg) {
        e(LOG_TAG, msg);
    }

    public void i(String tag, String msg) {
        if (LOG_SWITCH) {
            Log.i(tag, msg);
        }
    }

    public void w(String tag, String msg) {
        if (LOG_SWITCH) {
            Log.w(tag, msg);
        }
    }

    public void e(String tag, String msg) {
        if (LOG_SWITCH) {
            Log.e(tag, msg);
        }
    }
}
