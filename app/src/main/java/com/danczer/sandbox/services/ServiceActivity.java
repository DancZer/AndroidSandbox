package com.danczer.sandbox.services;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.danczer.sandbox.R;

public class ServiceActivity extends AppCompatActivity {

    private TextView startedServiceResultTextView;
    private TextView intentServiceResultTextView;

    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        startedServiceResultTextView = findViewById(R.id.textViewStarted);
        intentServiceResultTextView = findViewById(R.id.textViewIntent);

        counter = 0;
    }

    public void onStartClick(View view) {

        Intent intent = new Intent(ServiceActivity.this, MyService.class);

        intent.putExtra("sleepTime", 10);
        intent.putExtra("receiver", new MyResultBuilder(new Handler(), startedServiceResultTextView));

        startService(intent);
    }

    public void onStopClick(View view) {

        Intent intent = new Intent(ServiceActivity.this, MyService.class);

        stopService(intent);

        startedServiceResultTextView.setText("Service stopped!");
    }


    public void onIntentStartClick(View view) {
        Intent intent = new Intent(ServiceActivity.this, MyIntentService.class);

        intent.putExtra("counter", ++counter);
        intent.putExtra("sleepTime", 10);
        intent.putExtra("receiver", new MyResultBuilder(new Handler(), intentServiceResultTextView));

        startService(intent);
    }
}
