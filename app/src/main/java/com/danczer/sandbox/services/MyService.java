package com.danczer.sandbox.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyService extends Service
{

    private static  final String TAG = MyService.class.getSimpleName();

    private MyAsyncTask task;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "onCreate, Thread name "+Thread.currentThread().getName());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "onStartCommand, Thread name "+Thread.currentThread().getName());

        int sleepTime = intent.getIntExtra("sleepTime", 0);

        MyResultBuilder receiver = new MyResultBuilder((ResultReceiver) intent.getParcelableExtra("receiver"));

        receiver.sendText("Service started!");

        task = new MyAsyncTask(receiver);

        task.execute(sleepTime);

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        Log.i(TAG, "onBind, Thread name "+Thread.currentThread().getName());

        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "onDestroy, Thread name "+Thread.currentThread().getName());

        if(task != null){
            task.cancel(true);
        }
    }

    private static class MyAsyncTask extends AsyncTask<Integer, String, Void>{

        private final String TAG = MyAsyncTask.class.getSimpleName();

        private MyResultBuilder resultReceiver;

        private MyAsyncTask(MyResultBuilder resultReceiver){

            this.resultReceiver = resultReceiver;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.i(TAG, "onPreExecute, Thread name "+Thread.currentThread().getName());
        }

        @Override
        protected Void doInBackground(Integer... integers) {

            Log.i(TAG, "doInBackground, Thread name "+Thread.currentThread().getName());

            for (int sleepTime:integers) {
                //some delay in the service task
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                try {
                    while(sleepTime >0){
                        onProgressUpdate("Wake up in "+sleepTime+"s!");

                        Thread.sleep(1000);

                        --sleepTime;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    onProgressUpdate("Sleep interrupted!");
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            Log.i(TAG, "onProgressUpdate, Thread name "+Thread.currentThread().getName());

            for (String text:values) {
                resultReceiver.sendText(text);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.i(TAG, "onPostExecute, Thread name "+Thread.currentThread().getName());

            onProgressUpdate("Hello world!");
        }
    }
}
