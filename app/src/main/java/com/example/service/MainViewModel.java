package com.example.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<CustomService.CustomBinder> mBinder = new MutableLiveData<>();


    // Keeping this in here because it doesn't require a context
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder iBinder) {
            Log.e(MainViewModel.class.getSimpleName(), "ServiceConnection: connected to service.");
            // We've bound to CustomService, cast the IBinder and get MyBinder instance
            CustomService.CustomBinder binder = (CustomService.CustomBinder) iBinder;
            mBinder.postValue(binder);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.e(MainViewModel.class.getSimpleName(), "ServiceConnection: disconnected from service.");
            mBinder.postValue(null);
        }
    };

    public ServiceConnection getServiceConnection(){
        Log.e(MainViewModel.class.getSimpleName(),"getServiceConnection()");
        return serviceConnection;
    }

    public LiveData<CustomService.CustomBinder> getBinder(){
        Log.e(MainViewModel.class.getSimpleName(),"getBinder()");
        return mBinder;
    }

    private MutableLiveData<Boolean> mIsUSBMounted = new MutableLiveData<>();

    public LiveData<Boolean> getIsUSBMounted(){
        Log.e(MainViewModel.class.getSimpleName(),"getIsUSBMounted()");
        return mIsUSBMounted;
    }

    public void setIsUSBMounted(boolean isUpdating){
        Log.e(MainViewModel.class.getSimpleName(),"setIsUSBMounted()");
        mIsUSBMounted.postValue(isUpdating);
    }
}