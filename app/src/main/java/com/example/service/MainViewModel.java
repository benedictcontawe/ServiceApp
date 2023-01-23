package com.example.service;

import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.Manifest;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;

public class MainViewModel extends AndroidViewModel {

    static final private String TAG = MainViewModel.class.getSimpleName();
    static final Integer SETTINGS_PERMISSION_CODE = 1000;
    static final Integer NOTIFICATION_PERMISSION_CODE = 1010;

    public MainViewModel(Application application) {
        super(application);
        Log.d(TAG,"MainViewModel");
    }

    public String getPostNotificationsGranted() {
        Log.d(TAG,"MainViewModel getPostNotificationsGranted");
        if (isAndroidTiramisuAndPostNotificationsGranted())
            return "Granted!";
        else if (isAndroidTiramisuAndNotPostNotificationsGranted())
            return "Denied!";
        else return "Not Supported!";
    }

    public boolean isAndroidTiramisu() {
        Log.d(TAG,"MainViewModel isAndroidTiramisu");
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU;
    }

    public Boolean isAndroidTiramisuAndPostNotificationsGranted() {
        Log.d(TAG,"MainViewModel isAndroidTiramisuAndPostNotificationsGranted");
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && isPostNotificationsGranted();
    }

    public Boolean isAndroidTiramisuAndNotPostNotificationsGranted() {
        Log.d(TAG,"MainViewModel isAndroidTiramisuAndPostNotificationsGranted");
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !isPostNotificationsGranted();
    }

    public Boolean isAndroidTiramisuAndPostNotificationsGrantedOrNotTiramisu() {
        Log.d(TAG,"MainViewModel isAndroidTiramisuAndPostNotificationsGrantedOrNotTiramisu");
        return isAndroidTiramisuAndPostNotificationsGranted() || Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU;
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public Boolean isPostNotificationsGranted() {
        Log.d(TAG,"MainViewModel isPostNotificationsGranted");
        return ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermissionsPostNotifications(Activity activity) {
        Log.d(TAG,"MainViewModel launchActivityResultLauncher");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            ActivityCompat.requestPermissions(activity, new String[]{ Manifest.permission.POST_NOTIFICATIONS }, NOTIFICATION_PERMISSION_CODE);
    }

    public void launchActivityResultLauncher(ActivityResultLauncher<String> launcher) {
        Log.d(TAG,"MainViewModel launchActivityResultLauncher");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS);
    }

    public void showRationalDialog(Activity activity, String message) {
        Log.d(TAG,"showRationalDialog($activity,$message");
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Manifest Permissions");
        builder.setMessage(message);
        builder.setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showAppPermissionSettings(activity);
            }
        });
        builder.setNegativeButton("NOT NOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void showRationalDialog(Activity activity, String message, ActivityResultLauncher<Intent> activityResultLauncher) {
        Log.d(TAG,"showRationalDialog($activity,$message");
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Manifest Permissions");
        builder.setMessage(message);
        builder.setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showAppPermissionSettings(activity, activityResultLauncher);
            }
        });
        builder.setNegativeButton("NOT NOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showAppPermissionSettings(Activity activity) {
        Log.d(TAG, "showAppPermissionSettings()");
        final Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", activity.getPackageName(), null)
        );
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        activity.startActivityForResult(intent, SETTINGS_PERMISSION_CODE);
    }

    private void showAppPermissionSettings(Activity activity, ActivityResultLauncher<Intent> activityResultLauncher) {
        Log.d(TAG, "showAppPermissionSettings()");
        final Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", activity.getPackageName(), null)
        );
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        activityResultLauncher.launch(intent);
    }
}