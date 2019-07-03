package com.example.serviceapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private TextView txtData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtData = findViewById(R.id.txtData);


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (hasInternet()){
            txtData.setText("Network Connection is available");
        }
        else if (!hasInternet()){
            txtData.setText("Network Connection is not available");
        }

    }

    private boolean hasInternet(){
    boolean hasWifi = false;
    boolean hasMobileData = false;

    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for (NetworkInfo info : networkInfos){
        if (info.getTypeName().equalsIgnoreCase("WIFI"))
            if (info.isConnected())
                hasWifi = true;
        if (info.getTypeName().equalsIgnoreCase("MOBILE"))
            if (info.isConnected())
                hasMobileData = true;
        }
        return hasMobileData||hasWifi;
    }
}
