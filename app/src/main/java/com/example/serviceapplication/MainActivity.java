package com.example.serviceapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.serviceapplication.databinding.MainFragmentBinding;

public class MainActivity extends AppCompatActivity {

    private MainFragmentBinding binding;
    private MainViewModel viewModel;

    private Intent serviceBindIntent;
    CustomServiceConnector customServiceConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        binding.setViewModel(viewModel);
        //binding.setLifecycleOwner(getViewLifecycleOwner());

        serviceBindIntent = new Intent(this,CustomService.class);
        customServiceConnector= new CustomServiceConnector();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(MainActivity.class.getSimpleName(),"onStart()");

        customServiceConnector.getBinder().observe(this, new Observer<CustomService.LocalService>() {
            @Override
            public void onChanged(CustomService.LocalService localService) {
                if (localService != null){
                    Log.e(MainActivity.class.getSimpleName(), "onChanged() bound to service.");
                    //viewModel.setData(customServiceConnector.getcustomService().getFirstMessage());
                    binding.message.setText(customServiceConnector.getcustomService().getFirstMessage());
                }
                else {
                    Log.e(MainActivity.class.getSimpleName(), "onChanged() unbound from service");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        //startService(serviceBindIntent);
        Log.e(MainActivity.class.getSimpleName(),"bindService()");
        bindService(serviceBindIntent,customServiceConnector.getServiceConnection(),Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(MainActivity.class.getSimpleName(),"onStop()");

        Log.e(MainActivity.class.getSimpleName(),"unbindService()");
        if (customServiceConnector.getBinder() != null){
            unbindService(customServiceConnector.getServiceConnection());
        }
        else if (customServiceConnector.getBindState()){
            unbindService(customServiceConnector.getServiceConnection());
        }
        else {
            stopService(serviceBindIntent);
        }
    }
}
