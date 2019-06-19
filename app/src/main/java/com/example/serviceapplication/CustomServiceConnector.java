package com.example.serviceapplication;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CustomServiceConnector {

    private MutableLiveData<CustomService.LocalService> mBinder = new MutableLiveData<>();

    private CustomService customService;
    private boolean isBindConnected;

    CustomServiceConnector() {
        isBindConnected = false;

    }

    /**
     * @author {Benedict Contawe}
     * This will Create a Service Connection
     * Keeping this in here because it doesn't require a context
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder iBinder) {
            Log.e(CustomServiceConnector.class.getSimpleName(), "ServiceConnection: connected to service.");
            // We've bound to CustomService, cast the IBinder and get MyBinder instance
            CustomService.LocalService binder = (CustomService.LocalService) iBinder;
            customService = binder.getService();
            isBindConnected = true;
            mBinder.postValue(binder);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.e(CustomServiceConnector.class.getSimpleName(), "ServiceConnection: disconnected from service.");
            isBindConnected = true;
            mBinder.postValue(null);
        }
    };

    /**
     * @author {Benedict Contawe}
     * This class will get the Service Connection
     */
    public ServiceConnection getServiceConnection() {
        Log.e(CustomServiceConnector.class.getSimpleName(), "getServiceConnection()");
        return serviceConnection;
    }


    /**
     * @author {Benedict Contawe}
     * This class will get the Service Class
     */
    public CustomService getcustomService() {
        Log.e(CustomServiceConnector.class.getSimpleName(), "getcustomService()");
        return customService;
    }

    /**
     * @author {Benedict Contawe}
     * @deprecated use {@link #getBinder()} instead.
     */
    @Deprecated()
    public boolean getBindState() {
        Log.e(CustomServiceConnector.class.getSimpleName(), "getBindState()");
        return isBindConnected;
    }

    /**
     * @author {Benedict Contawe}
     * This class will get the binder
     */
    public LiveData<CustomService.LocalService> getBinder() {
        Log.e(CustomServiceConnector.class.getSimpleName(), "getBinder()");
        return mBinder;
    }
}
