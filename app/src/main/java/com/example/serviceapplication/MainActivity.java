package com.example.serviceapplication;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends FragmentActivity {

    //private Intent serviceBindIntent;
    //CustomServiceConnector customServiceConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //serviceBindIntent = new Intent(this,CustomService.class);
        //customServiceConnector= new CustomServiceConnector();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(MainActivity.class.getSimpleName(),"onStart()");

        //startService(service);
        //Log.e(MainActivity.class.getSimpleName(),"bindService()");
        //bindService(serviceBindIntent,customServiceConnector.getServiceConnection(),Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(MainActivity.class.getSimpleName(),"onStop()");

        //Log.e(MainActivity.class.getSimpleName(),customServiceConnector.getcustomService().getFirstMessage());

        /*
        if (customServiceConnector.getBindState()){
            unbindService(customServiceConnector.getServiceConnection());
        }
        else {
            stopService(serviceBindIntent);
        }
        */
    }
}
