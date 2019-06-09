package com.example.serviceapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private CustomService mService;
    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(MainActivity.class.getSimpleName(),"onCreate()");

        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        setObservers();
    }

    private void setObservers(){
        mViewModel.getBinder().observe(this, new Observer<CustomService.CustomBinder>() {
            @Override
            public void onChanged(@Nullable CustomService.CustomBinder customBinder) {
                if(customBinder == null){
                    Log.e(MainActivity.class.getSimpleName(), "onChanged() unbound from service");
                }
                else {
                    Log.e(MainActivity.class.getSimpleName(), "onChanged() bound to service.");
                    mService = customBinder.getService();
                    updateService();
                }
            }
        });

        mViewModel.getIsUSBMounted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                final Handler handler = new Handler();
                final Runnable runnable = new Runnable() {

                    @Override
                    public void run() {
                        if(mViewModel.getIsUSBMounted().getValue()){
                            if (mViewModel.getBinder().getValue() != null){
                                // meaning the service is bound
                                Log.e(MainActivity.class.getSimpleName(),"Data from Service to Activity " + mService.getData());
                            }
                            handler.postDelayed(this, 100);
                        }
                        else {
                            handler.removeCallbacks(this);
                        }
                    }
                };

                // control what the button shows
                if(aBoolean){
                    Log.e(MainActivity.class.getSimpleName(),"Data from Service to Activity " + mService.getData());
                    handler.postDelayed(runnable, 100);

                }
            }
        });
    }

    private void updateService(){
        Log.e(MainActivity.class.getSimpleName(),"updateService()");
        if(mService != null) {
            if (mService.getIsPaused()) {
                mService.unPauseTask();
                mViewModel.setIsUSBMounted(true);
            } else {
                mService.pauseTask();
                mViewModel.setIsUSBMounted(false);
            }
        }
    }

    private void bindService(){
        Log.e(MainActivity.class.getSimpleName(),"bindService()");
        Intent serviceBindIntent =  new Intent(this, CustomService.class);
        bindService(serviceBindIntent, mViewModel.getServiceConnection(), Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(MainActivity.class.getSimpleName(),"onResume()");
        bindService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(MainActivity.class.getSimpleName(),"onStop()");
        mViewModel.setIsUSBMounted(false);
        if (mViewModel.getBinder() != null){
            unbindService(mViewModel.getServiceConnection());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(MainActivity.class.getSimpleName(),"onDestroy()");

    }
}
