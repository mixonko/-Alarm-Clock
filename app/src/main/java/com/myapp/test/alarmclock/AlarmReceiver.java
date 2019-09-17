package com.myapp.test.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.myapp.test.alarmclock.myAppContext.MyApplication;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

//        int s = intent.getIntExtra("extra", "wake up");
            Toast.makeText(MyApplication.getAppContext(), "wake up", Toast.LENGTH_LONG).show();

    }

}
