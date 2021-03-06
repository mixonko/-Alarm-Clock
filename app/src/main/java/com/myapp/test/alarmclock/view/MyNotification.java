package com.myapp.test.alarmclock.view;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.renderscript.RenderScript;

import com.myapp.test.alarmclock.R;
import com.myapp.test.alarmclock.myAppContext.MyApplication;
import com.myapp.test.alarmclock.receiver.NotificationCancelReceiver;


import static androidx.core.content.ContextCompat.getSystemService;
import static com.myapp.test.alarmclock.view.MainActivity.SCREEN_OFF;

public class MyNotification {

    private static MyNotification instatce = new MyNotification();

    private NotificationManager notificationManager;
    private Ringtone ringtone;
    private Vibrator vibrator;
    public static final String ID_EXTRA = "id_extra";
    public static final String TITLE_EXTRA = "title_extra";
    public static final String TEXT_EXTRA = "text_extra";

    private MyNotification(){}

    public static MyNotification getMyNotification(){
        return instatce;
    }

    public void showNotification(String title, String text, int id){

        final String NOTIFICATION_CHANNEL_ID = String.valueOf(id);
        Notification.Builder mBuilder = new Notification.Builder(MyApplication.getAppContext());
        notificationManager = (NotificationManager) MyApplication.getAppContext().
                getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(MyApplication.getAppContext(), NotificationCancelReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getAppContext(),
                0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Action action = new Notification.Action(R.mipmap.ic_alarm,
                MyApplication.getAppContext().getResources().
                        getString(R.string.disable_alarm), pendingIntent);

        Intent alarmIntent = new Intent(MyApplication.getAppContext(), AlarmActivity.class);
        alarmIntent.putExtra(ID_EXTRA, id);
        alarmIntent.putExtra(TITLE_EXTRA, title);
        alarmIntent.putExtra(TEXT_EXTRA, text);
        PendingIntent pendingAlarmIntent =   PendingIntent.getActivity(MyApplication.getAppContext(), 1, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        mBuilder.setSmallIcon(R.mipmap.ic_alarm);
        mBuilder.setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(false) ;

        if (SCREEN_OFF){
            mBuilder.setFullScreenIntent(pendingAlarmIntent, true);
        }

        mBuilder.addAction(action);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, String.valueOf(id), NotificationManager.IMPORTANCE_HIGH );

            notificationChannel.enableLights(true);

            assert notificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);

        }
        assert notificationManager != null;
        notificationManager.notify(id, mBuilder.build());

    }


    public void deleteNotification(int id){
        notificationManager.cancel(id);

    }

    public void playRingtone(Uri uri){
        try {
            ringtone = RingtoneManager.getRingtone(MyApplication.getAppContext(), uri);
            ringtone.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopRingtone() {
        try {
            ringtone.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startVibration(long milliseconds) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator = getSystemService(MyApplication.getAppContext(), Vibrator.class);
                long [] timings = new long[]{1000, 1000, 1000, 1000, 1000, 1000, 1000};
                vibrator.vibrate(VibrationEffect.createWaveform(timings, 5));
            } else {
                vibrator.vibrate(milliseconds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void stopVibration() {
        try {
            vibrator.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
