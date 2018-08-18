package com.danczer.sandbox.services;

import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.danczer.sandbox.R;

public class ServiceActivity extends AppCompatActivity {

    private TextView startedServiceResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        startedServiceResultTextView = findViewById(R.id.textViewStarted);
    }

    public void onStartClick(View view) {

        Intent intent = new Intent(ServiceActivity.this, AsyncTaskService.class);

        intent.putExtra("sleepTime", 10);
        intent.putExtra("receiver", new StartedServiceReceiver(new Handler()));

        startService(intent);
    }

    public void onStopClick(View view) {

        Intent intent = new Intent(ServiceActivity.this, AsyncTaskService.class);

        stopService(intent);

        startedServiceResultTextView.setText("Service stopped!");
    }

    private class StartedServiceReceiver extends ResultReceiver{

        private final String TAG = StartedServiceReceiver.class.getSimpleName();

        public StartedServiceReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            Log.i(TAG, "onReceiveResult, Thread name "+Thread.currentThread().getName());

            if(resultCode == 10){
                String text;

                if(resultData == null || (text = resultData.getString("text")) == null){
                    startedServiceResultTextView.setText(R.string.strings);
                    return;
                }

                startedServiceResultTextView.setText(text);
            }

        }
    }
}
