package com.example.service;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final private String TAG = MainActivity.class.getSimpleName();
    private Button showNotificationButton, startIntentButton, showPermissionButton;
    private EditText inputEditTextput;
    private MainViewModel viewModel;
    private TextView notificationEnabledText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate()");
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        showNotificationButton = (Button) findViewById (R.id.show_notification_button);
        inputEditTextput = (EditText) findViewById (R.id.input_edit_text);
        startIntentButton = (Button) findViewById (R.id.start_intent_buttton);
        notificationEnabledText = (TextView) findViewById (R.id.notification_enabled_text);
        showPermissionButton = (Button) findViewById (R.id.show_permission_button);

        showNotificationButton.setOnClickListener(this);
        startIntentButton.setOnClickListener(this);
        showPermissionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == showNotificationButton && viewModel.isAndroidTiramisuAndPostNotificationsGrantedOrNotTiramisu()) {
            Log.d(TAG,"showNotificationButton");
            ContextCompat.startForegroundService(
                    getApplicationContext(),
                CustomService.newIntent(this)
            );
        } else if (view == startIntentButton && viewModel.isAndroidTiramisuAndPostNotificationsGrantedOrNotTiramisu()) {
            Log.d(TAG,"startIntentButton");
            ContextCompat.startForegroundService(
                    getApplicationContext(),
                CustomIntentService.newIntent(
                    getApplicationContext(),
                    inputEditTextput.getText().toString()
                )
            );
        } else if (view == showPermissionButton) {
            //viewModel.showRationalDialog(this,"Go to App Permission Settings?");
            viewModel.showRationalDialog(this,"Go to App Permission Settings?", activityResultLauncher);
        } else if (viewModel.isAndroidTiramisuAndNotPostNotificationsGranted()) {
            viewModel.requestPermissionsPostNotifications(this);
            //viewModel.launchActivityResultLauncher(requestPermissionLauncher);
        }
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult (
        new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean value) {
                Log.d(TAG,"onActivityResult(" + value + ")");
            }
        }
    );

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult (
        new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult activityResult) {
                Log.d(TAG,"onActivityResult(" + activityResult + ")");
                if (activityResult.getResultCode() == Activity.RESULT_OK) {
                    Log.d(TAG,"activityResult.getResultCode() == Activity.RESULT_OK");
                } else if (activityResult.getResultCode() == Activity.RESULT_CANCELED) {
                    Log.d(TAG,"activityResult.getResultCode() == Activity.RESULT_CANCELED");
                }
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"onActivityResult(" + requestCode + "," + resultCode + ", " + data + ")");
        if (resultCode == Activity.RESULT_OK) {
            Log.d(TAG,"resultCode == Activity.RESULT_OK");
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.d(TAG,"resultCode == Activity.RESULT_CANCELED");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //region Logs for onRequestPermissionsResult parameters
        Log.d("PermissionsResult", "requestCode " + requestCode);
        Log.d("PermissionsResult", "permissions " + Arrays.toString(permissions));
        Log.d("PermissionsResult", "grantResults " + Arrays.toString(grantResults));
        //endregion
        //region Code for grantResults
        for (int grantResult : grantResults) {
            switch (grantResult) {
                case PackageManager.PERMISSION_GRANTED:
                    Log.d("PermissionsResult", "grantResult Allowed " + grantResult);
                    //requestGranted = true;
                    break;
                case PackageManager.PERMISSION_DENIED:
                    Log.d("PermissionsResult", "grantResult Denied " + grantResult);
                    break;
                default:
                    break;
            }
        }
        //endregion
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop()");
        stopService(
            CustomService.newIntent(
                getApplicationContext()
            )
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy()");
    }
}