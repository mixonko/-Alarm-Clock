package com.myapp.test.alarmclock.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.view.MainActivity;
import com.myapp.test.alarmclock.view.MyNotification;

import static com.myapp.test.alarmclock.model.Repository.database;

public class AlarmClockReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra(MainActivity.INTENT_EXTRA, 1);
        AlarmClock alarmClock = database.alarmClockDao().getAlarmClock(id);
        MyNotification myNotification = MyNotification.getMyNotification();
        myNotification.showNotification(alarmClock.getDescription(),
                alarmClock.getHour() + ":" + alarmClock.getMinute(), id);
        myNotification.playRingtone(Uri.parse(alarmClock.getRingtone()));
        myNotification.startVibration(20000);
    }
}
