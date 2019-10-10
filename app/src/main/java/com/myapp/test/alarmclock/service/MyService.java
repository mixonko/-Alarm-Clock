package com.myapp.test.alarmclock.service;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.myAppContext.MyApplication;
import com.myapp.test.alarmclock.view.MainActivity;
import com.myapp.test.alarmclock.view.MyNotification;

import java.util.Calendar;

import static com.myapp.test.alarmclock.model.Repository.database;
import static com.myapp.test.alarmclock.receiver.AlarmClockReceiver.SERVICE_INTENT;
import static com.myapp.test.alarmclock.view.MainActivity.ALARM_CLOCK_OFF;

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

        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int monday = alarmClock.getMonday();
        int tuesday = alarmClock.getTuesday();
        int wednesday = alarmClock.getWednesday();
        int thursday = alarmClock.getThursday();
        int friday = alarmClock.getFriday();
        int saturday = alarmClock.getSaturday();
        int sunday = alarmClock.getSunday();

        startNotification(alarmClock);

        if (monday == dayOfWeek || tuesday == dayOfWeek
                || wednesday == dayOfWeek || thursday == dayOfWeek
                || friday == dayOfWeek || saturday == dayOfWeek
                || sunday == dayOfWeek) {
            reuseAlarmClock(alarmClock);
        } else {
            alarmClockOff(alarmClock);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void startNotification(AlarmClock alarmClock) {
        MyNotification myNotification = MyNotification.getMyNotification();
        myNotification.showNotification(alarmClock.getDescription(),
                alarmClock.getHour() + ":" + alarmClock.getMinute(), id);
        myNotification.playRingtone(Uri.parse(alarmClock.getRingtone()));
        if (alarmClock.getVibration())
            myNotification.startVibration(20000);
    }

    private void reuseAlarmClock(AlarmClock alarmClock) {
        MainActivity mainActivity = new MainActivity();
        mainActivity.alarmClockOn(Integer.parseInt(alarmClock.getHour()),
                Integer.parseInt(alarmClock.getMinute()), alarmClock.getId());
    }

    private void alarmClockOff(AlarmClock alarmClock) {
        alarmClock.setAlarmClockOn(false);
        database.alarmClockDao().updateAlarmClock(alarmClock);

        Intent myIntent = new Intent();
        myIntent.setAction(ALARM_CLOCK_OFF);
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
