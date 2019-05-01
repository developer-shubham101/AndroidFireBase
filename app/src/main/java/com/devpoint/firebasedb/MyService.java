package com.devpoint.firebasedb;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.devpoint.firebasedb.utils.LogUtils;

public class MyService extends Service {

    private static final String TAG = "This is service";

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        LogUtils.d(TAG, "onStart");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        LogUtils.d(TAG, "onStartCommand");
        return Service.START_NOT_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        LogUtils.d(TAG,"onBind");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.d(TAG,"onDestroy");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        LogUtils.d(TAG,"onDestroy");
        super.onDestroy();
    }

    @Override
    public void onRebind(Intent intent) {
        LogUtils.d(TAG,"onRebind");
        super.onRebind(intent);
    }
}