package com.example.serviceapplication;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.os.Build;
import androidx.core.app.NotificationManagerCompat;

public class App extends Application {

    public static final int CHANNEL_ID1 = 0x00001;
    public static final int CHANNEL_ID2 = 0x00002;
    public static final int CHANNEL_ID3 = 0x00003;
    public static final int CHANNEL_ID4 = 0x00004;
    public static final int CHANNEL_ID5 = 0x00005;
    public static final String GROUP_ID_1 = "CustomServiceGroup1";
    public static final String CHANNEL_ID_1 = "CustomServiceChannel1";
    public static final String CHANNEL_ID_2 = "CustomServiceChannel2";
    public static final String CHANNEL_ID_3 = "CustomServiceChannel3";
    public static final String CHANNEL_ID_4 = "CustomServiceChannel4";
    public static final String CHANNEL_ID_5 = "CustomServiceChannel5";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannelGroup group1 = new NotificationChannelGroup(
                    GROUP_ID_1,
                    "Group 1"
            );

            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_ID_1,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel1.setDescription("This is Channel 1");
            channel1.setGroup(GROUP_ID_1);

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_ID_2,
                    "Channel 2",
                    NotificationManager.IMPORTANCE_HIGH
            );
            //channel2.setSound(null,null); //For No Sounds
            //channel2.enableVibration(false); //For No Vibration
            channel2.setDescription("This is Channel 2");
            channel2.setGroup(GROUP_ID_1);

            NotificationChannel channel3 = new NotificationChannel(
                    CHANNEL_ID_3,
                    "Channel 3",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel3.setDescription("This is Channel 3");
            channel3.setGroup(GROUP_ID_1);

            NotificationManagerCompat manager = NotificationManagerCompat.from(this);
            manager.createNotificationChannelGroup(group1);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
            manager.createNotificationChannel(channel3);
        }
    }
}