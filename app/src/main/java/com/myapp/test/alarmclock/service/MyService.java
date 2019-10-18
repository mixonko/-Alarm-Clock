package com.myapp.test.alarmclock.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.myAppContext.MyApplication;
import com.myapp.test.alarmclock.view.MyNotification;

import java.util.Calendar;
import java.util.List;

import static com.myapp.test.alarmclock.model.Repository.database;
import static com.myapp.test.alarmclock.other.RegisterAlarmClock.registerAlarmClock;
import static com.myapp.test.alarmclock.receiver.AlarmClockReceiver.SERVICE_INTENT;
import static com.myapp.test.alarmclock.view.MainActivity.UPDATE;

public class MyService extends Service {
    private int id;
    private AlarmClock alarmClock;
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
        alarmClock = database.alarmClockDao().getAlarmClock(id);

        if (alarmClock.getDaysOfWeek().getMonday() == 0 && alarmClock.getDaysOfWeek().getTuesday() == 0
                && alarmClock.getDaysOfWeek().getWednesday() == 0 && alarmClock.getDaysOfWeek().getThursday() == 0
                && alarmClock.getDaysOfWeek().getFriday() == 0 && alarmClock.getDaysOfWeek().getSaturday() == 0
                && alarmClock.getDaysOfWeek().getSunday() == 0) {
            startNotification(alarmClock);
            alarmClockOff(alarmClock);
        }else  {
            startNotification(alarmClock);
            reuseAlarmClock(alarmClock);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void startNotification(AlarmClock alarmClock) {
        MyNotification myNotification = MyNotification.getMyNotification();
        myNotification.showNotification(alarmClock.getDescription(),
                alarmClock.getHour() + ":" + alarmClock.getMinute(), id);
        myNotification.playRingtone(Uri.parse(alarmClock.getRingtonePath()));
        if (alarmClock.getVibration())
            myNotification.startVibration(20000);
    }

    private void reuseAlarmClock(AlarmClock alarmClock) {
        Long timeInMillis = registerAlarmClock(alarmClock.getId(), Integer.parseInt(alarmClock.getHour()),
                Integer.parseInt(alarmClock.getMinute()), alarmClock.getDaysOfWeek());
        alarmClock.setTimeInMillis(timeInMillis);
        updateDB(alarmClock);
        updateList();
    }

    private void alarmClockOff(AlarmClock alarmClock) {
        alarmClock.setAlarmClockOn(false);
        updateDB(alarmClock);
        updateList();
    }

    private void updateDB(AlarmClock alarmClock){
        database.alarmClockDao().updateAlarmClock(alarmClock);
    }

    private void updateList(){
        Intent myIntent = new Intent();
        myIntent.setAction(UPDATE);
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
