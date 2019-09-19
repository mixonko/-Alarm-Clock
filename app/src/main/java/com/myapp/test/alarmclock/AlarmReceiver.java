package com.myapp.test.alarmclock;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.myapp.test.alarmclock.contracts.RepositoryContract;
import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.model.Repository;
import com.myapp.test.alarmclock.myAppContext.MyApplication;


public class AlarmReceiver extends BroadcastReceiver {
    private RepositoryContract repository;

    @Override
    public void onReceive(Context context, Intent intent) {
        repository = new Repository();

        int s = intent.getIntExtra("extra", 1);
        AlarmClock alarmClock = repository.getAlarmClock(s);

        long[] vibrate = new long[]{100, 100, 100, 100, 100, 100, 100, 100};
        final int notificationId = alarmClock.getId();
        final String NOTIFICATION_CHANNEL_ID = String.valueOf(alarmClock.getId());

        Notification.Builder mBuilder = new Notification.Builder(MyApplication.getAppContext());
//        Intent myIntent = new Intent(MyApplication.getAppContext(), AlarmReceiver.class);
//        myIntent.setAction(myIntent.ACTION_DELETE);
//        PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.getAppContext(), 0, myIntent, 0);
        NotificationManager mNotificationManager = (NotificationManager) MyApplication.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder.setSmallIcon(R.mipmap.ic_alarm);
        mBuilder.setContentTitle(alarmClock.getDescription())
                .setContentText(alarmClock.getHour() + ":" + alarmClock.getMinute())
                .setAutoCancel(false);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            if (alarmClock.getVibration()){
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(vibrate);
            }
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(notificationId, mBuilder.build());
    }
}



