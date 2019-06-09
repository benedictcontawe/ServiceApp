package com.example.serviceapplication;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<CustomService.CustomBinder> mBinder = new MutableLiveData<>();


    // Keeping this in here because it doesn't require a context
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder iBinder) {
            Log.e(MainActivityViewModel.class.getSimpleName(), "ServiceConnection: connected to service.");
            // We've bound to CustomService, cast the IBinder and get MyBinder instance
            CustomService.CustomBinder binder = (CustomService.CustomBinder) iBinder;
            mBinder.postValue(binder);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.e(MainActivityViewModel.class.getSimpleName(), "ServiceConnection: disconnected from service.");
            mBinder.postValue(null);
        }
    };

    public ServiceConnection getServiceConnection(){
        Log.e(MainActivityViewModel.class.getSimpleName(),"getServiceConnection()");
        return serviceConnection;
    }

    public LiveData<CustomService.CustomBinder> getBinder(){
        Log.e(MainActivityViewModel.class.getSimpleName(),"getBinder()");
        return mBinder;
    }

    private MutableLiveData<Boolean> mIsUSBMounted = new MutableLiveData<>();

    public LiveData<Boolean> getIsUSBMounted(){
        Log.e(MainActivityViewModel.class.getSimpleName(),"getIsUSBMounted()");
        return mIsUSBMounted;
    }

    public void setIsUSBMounted(boolean isUpdating){
        Log.e(MainActivityViewModel.class.getSimpleName(),"setIsUSBMounted()");
        mIsUSBMounted.postValue(isUpdating);
    }
}
