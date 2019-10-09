package com.myapp.test.alarmclock.view.service;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.myAppContext.MyApplication;
import com.myapp.test.alarmclock.view.MyNotification;

import java.util.Calendar;

import static com.myapp.test.alarmclock.model.Repository.database;
import static com.myapp.test.alarmclock.receiver.AlarmClockReceiver.SERVICE_INTENT;
import static com.myapp.test.alarmclock.view.MainActivity.STOP_ALARM_RECEIVER;

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

//        Calendar calendar = Calendar.getInstance();
//        int monday = alarmClock.getMonday();
//        int tuesday = alarmClock.getTuesday();
//        int wednesday = alarmClock.getWednesday();
//        int thursday = alarmClock.getThursday();
//        int friday = alarmClock.getFriday();
//        int saturday = alarmClock.getSaturday();
//        int sunday = alarmClock.getSunday();
//
//        if (monday == calendar.get(Calendar.DAY_OF_WEEK) || tuesday == calendar.get(Calendar.DAY_OF_WEEK)
//                || wednesday == calendar.get(Calendar.DAY_OF_WEEK) || thursday == calendar.get(Calendar.DAY_OF_WEEK)
//                || friday == calendar.get(Calendar.DAY_OF_WEEK) || saturday == calendar.get(Calendar.DAY_OF_WEEK)
//                || sunday == calendar.get(Calendar.DAY_OF_WEEK)){
//            startNotification(alarmClock);
//
//        }
                    startNotification(alarmClock);

        return super.onStartCommand(intent, flags, startId);
    }

    private void startNotification(AlarmClock alarmClock){
        MyNotification myNotification = MyNotification.getMyNotification();
        myNotification.showNotification(alarmClock.getDescription(),
                alarmClock.getHour() + ":" + alarmClock.getMinute(), id);
        myNotification.playRingtone(Uri.parse(alarmClock.getRingtone()));
        if (alarmClock.getVibration())
            myNotification.startVibration(20000);

        alarmClock.setAlarmClockOn(false);
        database.alarmClockDao().updateAlarmClock(alarmClock);

        Intent myIntent = new Intent();
        myIntent.setAction(STOP_ALARM_RECEIVER);
        MyApplication.getAppContext().sendBroadcast(myIntent);
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
