package com.myapp.test.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.myapp.test.alarmclock.myAppContext.MyApplication;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TAG_ LOG" , "будильник работает");
        Toast.makeText(MyApplication.getAppContext(), "asdasd", Toast.LENGTH_LONG).show();

    }

}
