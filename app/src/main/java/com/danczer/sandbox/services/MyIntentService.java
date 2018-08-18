package com.danczer.sandbox.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyIntentService extends IntentService
{
    private static  final String TAG = MyIntentService.class.getSimpleName();

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "onCreate, Thread name "+Thread.currentThread().getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "onHandleIntent, Thread name "+Thread.currentThread().getName());

        if(intent == null) return;

        int counter = intent.getIntExtra("counter", -1);
        int sleepTime = intent.getIntExtra("sleepTime", 0);

        MyResultBuilder receiver = new MyResultBuilder((ResultReceiver) intent.getParcelableExtra("receiver"));

        receiver.sendText(counter+". Intent handled by service!");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            while(sleepTime >0){
                receiver.sendText(counter+". wake up in "+sleepTime+"s!");

                Thread.sleep(1000);

                --sleepTime;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            receiver.sendText(counter+". sleep interrupted!");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "onDestroy, Thread name "+Thread.currentThread().getName());
    }
}
