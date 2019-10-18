package com.myapp.test.alarmclock.other;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import com.myapp.test.alarmclock.entity.DaysOfWeek;
import com.myapp.test.alarmclock.myAppContext.MyApplication;
import com.myapp.test.alarmclock.receiver.AlarmClockReceiver;

import java.util.Calendar;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

public class RegisterAlarmClock {
    private static final String INTENT_EXTRA = "INTENT_EXTRA";

    private RegisterAlarmClock() {}

    public static Long registerAlarmClock(int id, int hour, int minute, DaysOfWeek daysOfWeek) {
        Calendar calendar = Calendar.getInstance();
        List<Integer> days= daysOfWeek.getDays();

        if (daysOfWeek.getMonday() == 0 && daysOfWeek.getTuesday() == 0
                && daysOfWeek.getWednesday() == 0 && daysOfWeek.getThursday() == 0
                && daysOfWeek.getFriday() == 0 && daysOfWeek.getSaturday() == 0
                && daysOfWeek.getSunday() == 0) {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            if (calendar.before(Calendar.getInstance())) {
                calendar.add(Calendar.DATE, 1);
            }
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            for (int i = 0; i < days.size(); i++) {
                if (days.contains(calendar.get(Calendar.DAY_OF_WEEK))) {
                    if (calendar.before(Calendar.getInstance())) {
                        calendar.add(Calendar.DATE, 1);
                        continue;
                    }
                    break;
                } else {
                    calendar.add(Calendar.DATE, 1);
                }
            }
        }

        Long timeInMillis = calendar.getTimeInMillis();
        Intent intent = new Intent(MyApplication.getAppContext(), AlarmClockReceiver.class);
        intent.addFlags(Intent.FLAG_RECEIVER_NO_ABORT);
        intent.putExtra(INTENT_EXTRA, id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getAppContext(),
                id, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) MyApplication.getAppContext().getSystemService(ALARM_SERVICE);
        AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(timeInMillis, pendingIntent);
        alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);

        return timeInMillis;
}

    public static void unRegisterAlarmClock(int id) {
        Intent intent = new Intent(MyApplication.getAppContext(), AlarmClockReceiver.class);
        intent.addFlags(Intent.FLAG_RECEIVER_NO_ABORT);
        intent.putExtra(INTENT_EXTRA, id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getAppContext(),
                id, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) MyApplication.getAppContext().getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();

    }
}
