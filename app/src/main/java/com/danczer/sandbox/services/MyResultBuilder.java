package com.danczer.sandbox.services;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.danczer.sandbox.R;

public class MyResultBuilder extends ResultReceiver{

    private static final int TEXT_CODE = 10;

    private static final String TAG = MyResultBuilder.class.getSimpleName();

    private final ResultReceiver receiver;
    private final TextView textView;

    public MyResultBuilder(ResultReceiver receiver){
        super(null);
        this.receiver = receiver;
        textView = null;
    }

    public MyResultBuilder(Handler handler, @NonNull TextView textView) {
        super(handler);
        this.receiver = null;
        this.textView = textView;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);

        Log.i(TAG, "onReceiveResult, Thread name " + Thread.currentThread().getName());

        if(this.receiver == null && this.textView != null) {

            if (resultCode == TEXT_CODE) {
                String text;

                if (resultData == null || (text = resultData.getString("text")) == null) {
                    textView.setText(R.string.strings);
                    return;
                }

                textView.setText(text);
            }
        }else if(this.receiver != null){
            this.receiver.send(resultCode, resultData);
        }
    }

    public void sendText(String text){
        Bundle bundle = new Bundle();

        bundle.putString("text", text);

        if(receiver != null){
            receiver.send(TEXT_CODE, bundle);
        }else{
            send(TEXT_CODE, bundle);
        }
    }
}