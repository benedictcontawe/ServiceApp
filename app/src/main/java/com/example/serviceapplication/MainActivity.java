package com.example.serviceapplication;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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



        if (InternetConnection.hasInternet((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE))){
            txtData.setText("Network Connection is available");
        }
        else if (!(InternetConnection.hasInternet((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE)))){
            txtData.setText("Network Connection is not available");
        }

    }



}
