package com.example.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class CustomService extends Service {

    static private String TAG = CustomService.class.getSimpleName();

    public CustomService() {
        Log.d(TAG,"CustomService()");
    }

    static public Intent newIntent(Context context) {
        Log.d(TAG,"newIntent()");
        return new Intent(context, CustomService.class);
    }

    static public Intent newIntent(Context context, String contentText) {
        Intent serviceIntent = new Intent(context, CustomService.class);
        serviceIntent.putExtra("inputExtra", contentText);
        return serviceIntent;
    }

    private NotificationManagerCompat notificationManagerCompat;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate()");
        notificationManagerCompat = NotificationManagerCompat.from(this);
    }

    private PendingIntent getPendingIntent(Context context) {
        Intent notificationIntent = new Intent(context, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .putExtra("DATA","Hello Service");
        return PendingIntent.getActivity(context,
                1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent showAppPermissionSettings() {
        Intent intent =  new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", this.getPackageName(), null));

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        return PendingIntent.getActivity(this,
                0, intent, 0);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags, startId);
        Log.d(TAG,"onStartCommand()");

        Notification notification = createNotification();

        startForeground(App.CHANNEL_ID1, notification);

        //do heavy work on a background thread
        //stopSelf();

        return START_STICKY;
        //return START_NOT_STICKY;
        //return START_REDELIVER_INTENT;
    }

    private Notification createNotification() {
        return new NotificationCompat.Builder(this, App.CHANNEL_ID_1)
            .setOngoing(true)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
            //.setSmallIcon(R.drawable.ic_android_black)
            //.setContentTitle("CustomService is running")
            //.setContentText("Touch for more information or to stop the app")
            //.setContentIntent(showAppPermissionSettings()) //onContentTapped
            //.setDeleteIntent(null) //onSwipedAway
            .build();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind(" + intent + ")");
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy()");
    }
}