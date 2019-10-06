package com.myapp.test.alarmclock.view.service;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.myAppContext.MyApplication;
import com.myapp.test.alarmclock.view.MyNotification;

import static com.myapp.test.alarmclock.model.Repository.database;
import static com.myapp.test.alarmclock.receiver.AlarmClockReceiver.SERVICE_INTENT;

public class MyService extends Service {
    private int id;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        id = intent.getIntExtra(SERVICE_INTENT, 1);
        AlarmClock alarmClock = database.alarmClockDao().getAlarmClock(id);
        MyNotification myNotification = MyNotification.getMyNotification();
        myNotification.showNotification(alarmClock.getDescription(),
                alarmClock.getHour() + ":" + alarmClock.getMinute(), id);
        myNotification.playRingtone(Uri.parse(alarmClock.getRingtone()));
        myNotification.startVibration(20000);

        alarmClock.setAlarmClockOn(false);
        database.alarmClockDao().updateAlarmClock(alarmClock);

        Intent myIntent = new Intent();
        myIntent.setAction("aaa");
        MyApplication.getAppContext().sendBroadcast(myIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyNotification myNotification = MyNotification.getMyNotification();
        myNotification.stopRingtone();
        myNotification.stopVibration();
        myNotification.deleteNotification(id);
    }

}
