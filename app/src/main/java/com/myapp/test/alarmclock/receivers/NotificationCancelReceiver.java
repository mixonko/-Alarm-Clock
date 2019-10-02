package com.myapp.test.alarmclock.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.view.MyNotification;

import static com.myapp.test.alarmclock.model.Repository.database;

public class NotificationCancelReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra(MyNotification.CANCEL_ALARM_CLOCK_EXTRA, 1);
        AlarmClock alarmClock = database.alarmClockDao().getAlarmClock(id);
        alarmClock.setAlarmClockOn(false);
        database.alarmClockDao().updateAlarmClock(alarmClock);
        MyNotification myNotification = MyNotification.getMyNotification();
        myNotification.stopRingtone();
        myNotification.stopVibration();
        myNotification.deleteNotification(id);
    }
}
