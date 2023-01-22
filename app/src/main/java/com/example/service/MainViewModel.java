package com.example.service;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.Manifest;
import android.os.Build;
import android.Manifest;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends AndroidViewModel {

    static final private String TAG = MainViewModel.class.getSimpleName();

    public MainViewModel(Application application) {
        super(application);
        Log.d(TAG,"MainViewModel");
    }

    public Boolean isAndroidTiramisuAndPostNotificationsGranted() {
        Log.d(TAG,"MainViewModel isAndroidTiramisuAndPostNotificationsGranted");
        return isAndroidTiramisu() && ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public Boolean isPostNotificationsGranted() {
        Log.d(TAG,"MainViewModel isPostNotificationsGranted");
        return ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isAndroidTiramisu() {
        Log.d(TAG,"MainViewModel isAndroidTiramisu");
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU;
    }

    public void launchActivityResultLauncher(ActivityResultLauncher<String> launcher) {
        Log.d(TAG,"MainViewModel launchActivityResultLauncher");
        launcher.launch(Manifest.permission.POST_NOTIFICATIONS);
        if (isAndroidTiramisu()) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }
    }

    public String getPostNotificationsGranted() {
        Log.d(TAG,"MainViewModel getPostNotificationsGranted");
        if (isAndroidTiramisuAndPostNotificationsGranted())
            return "Granted!";
        else return "Denied!";
    }
}