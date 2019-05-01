package com.devpoint.firebasedb.utils;

import android.util.Log;

import com.devpoint.firebasedb.BuildConfig;


public class LogUtils {
    private static boolean DEBUG = BuildConfig.DEBUG;


    public static void d(String TAG, String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }
    }

    public static void e(String TAG, String message) {
        if (DEBUG) {
            Log.e(TAG, message);
        }

    }
}
