package com.example.service;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final private String TAG = MainActivity.class.getSimpleName();
    private Button showNotificationButton;
    private CustomService mService;
    private MainViewModel viewModel;
    private TextView notificationEnabledText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate()");
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        showNotificationButton = (Button) findViewById (R.id.show_notification_button);
        notificationEnabledText = (TextView) findViewById (R.id.notification_enabled_text);
        showNotificationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == showNotificationButton) {
            if (viewModel.isAndroidTiramisuAndPostNotificationsGranted()) {
                //showDummyNotification()
            } else {
                viewModel.launchActivityResultLauncher(requestPermissionLauncher);
            }
        }
    }

    ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult (
        new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean value) {
                Log.d(TAG,"onActivityResult(" + value + ")");
            }
        }
    );

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume()");
        notificationEnabledText.setText(
                viewModel.getPostNotificationsGranted()
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy()");
    }
}