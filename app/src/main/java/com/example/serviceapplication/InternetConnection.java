package com.example.serviceapplication;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class InternetConnection {

    public static boolean hasInternet(ConnectivityManager connectivityManager){
        boolean hasWifi = false;
        boolean hasMobileData = false;

        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for (NetworkInfo info : networkInfos){
            if (info.getTypeName().equalsIgnoreCase("WIFI"))
                if (info.isConnected())
                    hasWifi = true;
            if (info.getTypeName().equalsIgnoreCase("MOBILE"))
                if (info.isConnected())
                    hasMobileData = true;
        }
        return hasMobileData || hasWifi;
    }
}
