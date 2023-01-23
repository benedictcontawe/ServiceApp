package com.example.service;

import android.app.IntentService;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class CustomIntentService extends IntentService {

    private static final String TAG = CustomIntentService.class.getSimpleName();
    private PowerManager.WakeLock wakeLock; //force the device to stay on

    public CustomIntentService() {
        super(TAG);
        setIntentRedelivery(true);
        Log.d(TAG,"CustomIntentService()");
    }

    static public Intent newIntent(Context context) {
        Log.d(TAG,"newIntent()");
        return new Intent(context, CustomIntentService.class);
    }

    static public Intent newIntent(Context context, String input) {
        Log.d(TAG,"newIntent(" + input + ")");
        return new Intent(context, CustomIntentService.class).putExtra("inputExtra", input);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate()");

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Example:Wakelock");
        wakeLock.acquire(10*60*1000L /*10 minutes*/);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(App.CHANNEL_ID2, createNotification());
        }
    }

    private Notification createNotification() {
        Log.d(TAG,"createNotification()");
        return new NotificationCompat.Builder(this, App.CHANNEL_ID_2)
        .setContentTitle("Example CustomIntentService")
        .setContentText("Running...")
        .setSmallIcon(R.drawable.ic_android_black)
        .build();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG,"onHandleIntent()");

        String input = null;
        if (intent != null) {
            input = intent.getStringExtra("inputExtra");
        }

        for (int i = 0; i < 10; i++) {
            Log.d(TAG, input + " - " + i);
            SystemClock.sleep(1000);
        }
    }

    @Nullable
    @Override
    public ComponentName startService(Intent service) {
        Log.d(TAG,"startService(" +service + ")");
        return super.startService(service);
    }

    @Nullable
    @Override
    public ComponentName startForegroundService(Intent service) {
        Log.d(TAG,"startForegroundService(" +service + ")");
        return super.startForegroundService(service);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Destroy()");
        wakeLock.release();
        Log.d(TAG,"Wake Lock Released");
    }
}