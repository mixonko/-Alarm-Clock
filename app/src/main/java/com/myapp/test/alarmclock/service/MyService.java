package com.myapp.test.alarmclock.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.myAppContext.MyApplication;
import com.myapp.test.alarmclock.receiver.AlarmClockReceiver;
import com.myapp.test.alarmclock.view.MyNotification;

import java.util.Calendar;

import static com.myapp.test.alarmclock.model.Repository.database;
import static com.myapp.test.alarmclock.other.RegisterAlarmClock.registerAlarmClock;
import static com.myapp.test.alarmclock.receiver.AlarmClockReceiver.SERVICE_INTENT;
import static com.myapp.test.alarmclock.view.MainActivity.INTENT_EXTRA;
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

        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int monday = alarmClock.getMonday();
        int tuesday = alarmClock.getTuesday();
        int wednesday = alarmClock.getWednesday();
        int thursday = alarmClock.getThursday();
        int friday = alarmClock.getFriday();
        int saturday = alarmClock.getSaturday();
        int sunday = alarmClock.getSunday();


        if (monday == 0 && tuesday == 0
                && wednesday == 0 && thursday == 0
                && friday == 0 && saturday == 0
                && sunday == 0) {
            startNotification(alarmClock);
            alarmClockOff(alarmClock);
        }

        if (monday == dayOfWeek || tuesday == dayOfWeek
                || wednesday == dayOfWeek || thursday == dayOfWeek
                || friday == dayOfWeek || saturday == dayOfWeek
                || sunday == dayOfWeek) {
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
                Integer.parseInt(alarmClock.getMinute()), alarmClock.getMonday(),
                alarmClock.getTuesday(), alarmClock.getWednesday(), alarmClock.getThursday(),
                alarmClock.getFriday(), alarmClock.getSaturday(), alarmClock.getSunday());
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
