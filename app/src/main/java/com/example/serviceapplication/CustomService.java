package com.example.serviceapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class CustomService extends Service {

    private final IBinder mBinder = new LocalService();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class LocalService extends Binder {
        CustomService getService(){
            return CustomService.this;
        }
    }

    public String getFirstMessage() {
        return  "Service Application Now Connected";
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.e(CustomService.class.getSimpleName(),"onTaskRemoved()");
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(CustomService.class.getSimpleName(),"onDestroy()");
    }
}
