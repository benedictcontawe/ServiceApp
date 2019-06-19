package com.example.serviceapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.serviceapplication.databinding.MainFragmentBinding;

public class MainFragment extends Fragment {

    private MainFragmentBinding binding;
    private MainViewModel viewModel;

    private Intent serviceBindIntent;
    private CustomServiceConnector customServiceConnector;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MainFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        serviceBindIntent = new Intent(getContext(),CustomService.class);
        customServiceConnector= new CustomServiceConnector();
    }

    @Override
    public void onStart() {
        super.onStart();
        customServiceConnector.getBinder().observe(getActivity(), new Observer<CustomService.LocalService>() {
            @Override
            public void onChanged(CustomService.LocalService localService) {
                if (localService != null){
                    Log.e(MainActivity.class.getSimpleName(), "onChanged() bound to service.");
                    viewModel.setData(customServiceConnector.getcustomService().getFirstMessage());
                }
                else {
                    Log.e(MainActivity.class.getSimpleName(), "onChanged() unbound from service");
                }
            }
        });

        //viewModel.setData("Hello World");
    }

    @Override
    public void onResume() {
        super.onResume();

        //startService(service);
        Log.e(MainActivity.class.getSimpleName(),"bindService()");
        getActivity().bindService(serviceBindIntent,customServiceConnector.getServiceConnection(), Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.e(MainActivity.class.getSimpleName(),customServiceConnector.getcustomService().getFirstMessage());

        if (customServiceConnector.getBinder() != null){
            getActivity().unbindService(customServiceConnector.getServiceConnection());
        }
        else if (customServiceConnector.getBindState()){
            getActivity().unbindService(customServiceConnector.getServiceConnection());
        }
        else {
            getActivity().stopService(serviceBindIntent);
        }
    }
}
