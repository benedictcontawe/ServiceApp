package com.example.serviceapplication;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class CustomService extends Service {

    static private String TAG = CustomService.class.getSimpleName();

    static private CustomService instance;

    static public CustomService getInstance() {
        if (instance == null) {
            instance = new CustomService();
        }
        return instance;
    }

    static public Intent newIntent(Context context) {
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
        Log.e(TAG,"onCreate()");
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
        Log.e(TAG,"onStartCommand()");

        Notification notification = createNotification1(this);

        //notificationManagerCompat.notify(App.CHANNEL_ID1,notification);
        startForeground(App.CHANNEL_ID1,notification);

        //do heavy work on a background thread
        //stopSelf();

        return START_STICKY;
        //return START_NOT_STICKY;
        //return START_REDELIVER_INTENT;
    }

    public Notification createNotification1(Context context) {
        return new NotificationCompat.Builder(context, App.CHANNEL_ID_1)
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

    public void createNotification2(Context context, String title, String message) {
        Log.e(TAG,"createNotification()");
        if (notificationManagerCompat == null)
            notificationManagerCompat = NotificationManagerCompat.from(context);

            Notification notification = new NotificationCompat.Builder(context, App.CHANNEL_ID_2)
                    .setOngoing(false)
                    .setAutoCancel(false)
                    //.setCategory(NotificationCompat.CATEGORY_CALL)
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSmallIcon(R.drawable.ic_android_black)
                    .setContentTitle(title) //Set title of Notification First Row
                    .setContentText(message) //Set Text for Notification Second Row
                    .setSubText(message) //Set title of Notification Third Row
                    .setOnlyAlertOnce(false)
                    //.setNotificationSilent()
                    //.setContentIntent(getPendingIntent(context)) //onContentTapped
                    //.setDeleteIntent(null) //onSwipedAway
                    //.addAction(0, "Reply", null)
                    //.addAction(0, "Call", null)
                    .build();

            notificationManagerCompat.notify(App.CHANNEL_ID2, notification);
    }

    public void createNotification3(Context context, String title, String message) {
        Log.e(TAG,"createNotification()");
        if (notificationManagerCompat == null)
            notificationManagerCompat = NotificationManagerCompat.from(context);

        Notification notification = new NotificationCompat.Builder(context, App.CHANNEL_ID_3)
                .setOngoing(false)
                .setAutoCancel(false)
                //.setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setSmallIcon(R.drawable.ic_android_black)
                .setContentTitle(title)
                .setContentText(message)
                //.setContentIntent(getPendingIntent(context)) //onContentTapped
                //.setDeleteIntent(null) //onSwipedAway
                //.addAction(0, "Reply", null)
                //.addAction(0, "Call", null)
                .build();

        notificationManagerCompat.notify(App.CHANNEL_ID3, notification);
    }

    public void createNotification4(Context context, String title, String message) {
        Log.e(TAG,"createNotification()");
        if (notificationManagerCompat == null)
            notificationManagerCompat = NotificationManagerCompat.from(context);

        Notification notification = new NotificationCompat.Builder(context, App.CHANNEL_ID_3)
                .setOngoing(false)
                .setAutoCancel(false)
                .setGroupSummary(true)
                .setGroup(App.GROUP_ID_1)
                //.setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setSmallIcon(R.drawable.ic_android_black)
                .setContentTitle(title)
                .setContentText(message)
                //.setContentIntent(getPendingIntent(context)) //onContentTapped
                //.setDeleteIntent(null) //onSwipedAway
                //.addAction(0, "Reply", null)
                //.addAction(0, "Call", null)
                .build();

        notificationManagerCompat.notify(App.CHANNEL_ID3, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy()");
    }
}
