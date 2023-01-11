package com.example.serviceapplication;

import static com.example.serviceapplication.App.CHANNEL_ID;

import android.app.IntentService;
import android.app.Notification;
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

    public CustomIntentService(){
        super(TAG);
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Example:Wakelock");
        wakeLock.acquire(10*60*1000L /*10 minutes*/);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Example CustomIntentService")
                    .setContentText("Running...")
                    .setSmallIcon(R.drawable.ic_android_black)
                    .build();

            startForeground(1, notification);
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String input = intent.getStringExtra("inputExtra");

        for (int i = 0; i < 10; i++) {
            Log.d(CustomIntentService.class.getSimpleName(), input + " - " + i);
            SystemClock.sleep(1000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(CustomIntentService.class.getSimpleName(),"Destroy()");

        wakeLock.release();
        Log.d(CustomIntentService.class.getSimpleName(),"Wake Lock Released");
    }
}