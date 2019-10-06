package com.myapp.test.alarmclock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.myapp.test.alarmclock.myAppContext.MyApplication;
import com.myapp.test.alarmclock.view.MainActivity;
import com.myapp.test.alarmclock.view.service.MyService;

public class AlarmClockReceiver extends BroadcastReceiver {
    public static String SERVICE_INTENT = "SERVICE_INTENT";
    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra(MainActivity.INTENT_EXTRA, 1);
        Intent myIntent = new Intent(MyApplication.getAppContext(), MyService.class);
        myIntent.putExtra(SERVICE_INTENT, id);
        MyApplication.getAppContext().startService(myIntent);

    }

}
