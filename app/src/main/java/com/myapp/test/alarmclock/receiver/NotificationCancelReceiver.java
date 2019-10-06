package com.myapp.test.alarmclock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.myapp.test.alarmclock.myAppContext.MyApplication;
import com.myapp.test.alarmclock.view.service.MyService;

public class NotificationCancelReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(MyApplication.getAppContext(), MyService.class);
        MyApplication.getAppContext().stopService(myIntent);
    }
}
