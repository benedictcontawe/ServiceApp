package com.example.serviceapplication;

import android.net.ConnectivityManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<Boolean> internetState = new MutableLiveData<>();
    private MutableLiveData<String> data = new MutableLiveData<>();

    public LiveData<Boolean> getInternetState() {
        return internetState;
    }

    public void startInternetState(ConnectivityManager connectivityManager) {
        if (InternetConnection.hasInternet(connectivityManager)){
            setData("Network Connection is available");
        }
        else if (!(InternetConnection.hasInternet(connectivityManager))){
            setData("Network Connection is not available");
        }
    }

    public LiveData<String> getData() {
        return data;
    }

    public void setData(String data){
        this.data.setValue(data);
    }
}
