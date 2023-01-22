package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class CustomService extends Service {

    static final private String TAG = CustomService.class.getSimpleName();
    private Handler mHandler;
    private Boolean mIsPaused;
    private String data;

    public CustomService() {
    }

    private final IBinder mBinder = new CustomBinder();

    public class CustomBinder extends Binder {
        CustomService getService(){
            return CustomService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate()");
        mHandler = new Handler();
        mIsPaused = true;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"onBind()");
        return mBinder;
    }

    public String getData(){
        return data;
    }

    public Boolean getIsPaused(){
        return mIsPaused;
    }

    public void startTask(){
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(mIsPaused){
                    Log.e(TAG,"startTask() Removing Callbacks");
                    mHandler.removeCallbacks(this); // remove callbacks from runnable
                    pauseTask();
                }
                else {
                    Log.e(TAG,"startTask() Run Progress");
                    //TODO: give data and read Data
                    data = "Hello";
                    mHandler.postDelayed(this, 100); // continue incrementing
                }
            }
        };
        mHandler.postDelayed(runnable, 100);
    }

    public void pauseTask(){
        Log.e(TAG,"pauseTask()");
        mIsPaused = true;
    }

    public void unPauseTask(){
        Log.e(TAG,"unPauseTask()");
        mIsPaused = false; startTask();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.e(TAG,"onTaskRemoved()");
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy()");
        mIsPaused = true;
    }
}