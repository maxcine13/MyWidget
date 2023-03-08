package com.linji.cabinetutil.util;

import android.util.Log;

import com.linji.cabinetutil.BuildConfig;

/**
 * Created by debian on 2018/6/14.
 * Life is a journey.
 * What we should care about is not where it's headed but what we see and how we feel.
 * describe: 打印帮助类
 */
public class LogUtil {
    private static final String TAG = "---log--->";

    public static void i(String s) {
        Log.i(TAG,s);

    }

    public static void e(String s) {
        Log.e(TAG,s);

    }

    public static void d(String s) {
        Log.d(TAG,s);

    }

    public static void w(String s) {
        Log.w(TAG,s);

    }

    public static void v(String s) {
        Log.v(TAG,s);
    }

}
