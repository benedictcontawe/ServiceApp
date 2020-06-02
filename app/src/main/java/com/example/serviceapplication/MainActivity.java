package com.example.serviceapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStartService,btnStopService,btnChannelHigh,btnChannelLow;
    private EditText editTextTitle,editTextMessage;
    private String title,message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextMessage = findViewById(R.id.edit_text_message);
        btnStartService = findViewById(R.id.button_start_service);
        btnStopService = findViewById(R.id.button_stop_service);
        btnChannelHigh = findViewById(R.id.button_channel_high);
        btnChannelLow = findViewById(R.id.button_channel_low);

        btnStartService.setOnClickListener(this);
        btnStopService.setOnClickListener(this);
        btnChannelHigh.setOnClickListener(this);
        btnChannelLow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_start_service:
                startService();
                break;
            case R.id.button_stop_service:
                stopService();
                break;
            case R.id.button_channel_high:
                sendOnChannelHigh();
                break;
            case R.id.button_channel_low:
                sendOnChannelLow();
                break;
            default:
                Toast.makeText(this,"Unknown Button",Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void updateInputs() {
        title = editTextTitle.getText().toString();
        message = editTextMessage.getText().toString();
    }

    public void startService() {
        ContextCompat.startForegroundService(
                this,
                CustomService.newIntent(this)
        );
    }

    public void stopService() {
        stopService(
                CustomService.newIntent(this)
        );
        //CustomService.getInstance().stopSelf();
    }

    public void sendOnChannelHigh() {
        updateInputs();
        CustomService.getInstance().createNotification2(this,title,message);
    }

    public void sendOnChannelLow() {
        updateInputs();
        CustomService.getInstance().createNotification3(this,title,message);
    }
}
