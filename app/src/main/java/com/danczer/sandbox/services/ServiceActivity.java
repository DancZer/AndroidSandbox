package com.danczer.sandbox.services;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.danczer.sandbox.R;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
    }

    public void onStartClick(View view) {

        Intent intent = new Intent(ServiceActivity.this, StartedService.class);

        startService(intent);
    }

    public void onStopClick(View view) {

        Intent intent = new Intent(ServiceActivity.this, StartedService.class);

        stopService(intent);
    }
}
