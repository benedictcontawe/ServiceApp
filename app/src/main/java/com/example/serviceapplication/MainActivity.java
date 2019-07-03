package com.example.serviceapplication;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.serviceapplication.databinding.MainBinder;

public class MainActivity extends AppCompatActivity {

    private MainBinder binding;
    private MainViewModel viewModel;
    private TextView txtData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        binding.setViewModel(viewModel);
        //binding.setLifecycleOwner();

        txtData = findViewById(R.id.txtData);
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewModel.getData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String data) {
                txtData.setText(data);
            }
        });

        viewModel.startInternetState((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE));
    }



}
