package com.myapp.test.alarmclock.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.myapp.test.alarmclock.R;
import com.myapp.test.alarmclock.contracts.MainContract;
import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.myAppContext.MyApplication;
import com.myapp.test.alarmclock.presenter.MainPresenter;
import com.myapp.test.alarmclock.view.adapter.ExampleAdapter;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private MainContract.Presenter presenter;
    private Button create;
    private RecyclerView recyclerView;
    private ExampleAdapter exampleAdapter;
    private LinearLayoutManager linearLayoutManager;
    public static final String ACTION_ON = "action_on";
    public static final String ACTION_OFF = "action_off";
    public static final String INTENT_EXTRA = "extra";
    public static final String ALARM_CLOCK_ID = "alarm_clock_id";
    private Notification.Builder mBuilder;
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);

        create = findViewById(R.id.create);
        recyclerView = findViewById(R.id.list);
        linearLayoutManager = new LinearLayoutManager(MyApplication.getAppContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onCreateButtonWasClicked();
            }
        });

        presenter.onCreateActivity();

        createBroadcastReceiver();
        createBroadcastReceiverOff();
}

    @Override
    public void setInfoText(String infoText) {

    }

    @Override
    public void startCreateActivity() {
        startActivity(new Intent(MyApplication.getAppContext(), CreateActivity.class));
    }

    @Override
    public void startChangeActivity(int id) {
        Intent intent = new Intent(MyApplication.getAppContext(), CreateActivity.class);
        intent.putExtra(ALARM_CLOCK_ID, id);
        startActivityForResult(intent, 1);
    }

    @Override
    public void setAdapter(final List<AlarmClock> list) {
        if (list.size() != 0) {
            exampleAdapter = new ExampleAdapter(list);
            recyclerView.setAdapter(exampleAdapter);
            exampleAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    AlarmClock alarmClock = list.get(position);
                    presenter.onItemWasClicked(alarmClock);
                }
            });
            exampleAdapter.setOnItemLongClickListener(new ExampleAdapter.OnItemLongClickListener() {
                @Override
                public void onItemLongClick(int position) {
                    AlarmClock alarmClock = list.get(position);
                    presenter.onItemWasLongClicked(alarmClock);
                }
            });
            exampleAdapter.setOnCheckedChangeListener(new ExampleAdapter.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(int position, CompoundButton compoundButton, boolean b) {
                    AlarmClock alarmClock = list.get(position);
                    presenter.onSwitchWasChanged(b, alarmClock);
                }
            });
        }

    }

    @Override
    public void alarmClockOn(int hour, int minute, int id) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
//        if(calendar.before(Calendar.getInstance())) {
//            calendar.add(Calendar.DATE, 1);
//        }
        Intent intent = new Intent(ACTION_ON);
        intent.putExtra(INTENT_EXTRA, id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getAppContext(),
                id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void alarmClockOff(int id) {
        Intent intent = new Intent(ACTION_ON);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getAppContext(),
                id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    @Override
    public void createNotification(int id, AlarmClock alarmClock) {
        long[] vibrate = new long[]{100, 100, 100, 100, 100, 100, 100, 100};
        final String NOTIFICATION_CHANNEL_ID = String.valueOf(alarmClock.getId());

        mBuilder = new Notification.Builder(MyApplication.getAppContext());
        mNotificationManager = (NotificationManager) MyApplication.getAppContext().
                getSystemService(Context.NOTIFICATION_SERVICE);

        Intent cancel = new Intent(ACTION_OFF);
        cancel.putExtra(INTENT_EXTRA, alarmClock.getId());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getAppContext(),
                0, cancel, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Action action = new Notification.Action(R.mipmap.ic_alarm,
                MyApplication.getAppContext().getResources().
                getString(R.string.disable_alarm), pendingIntent);

        mBuilder.setSmallIcon(R.mipmap.ic_alarm);
        mBuilder.setContentTitle(alarmClock.getDescription())
                .setContentText(alarmClock.getHour() + ":" + alarmClock.getMinute())
                .setAutoCancel(false);
        mBuilder.addAction(action);


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
        mNotificationManager.notify(id, mBuilder.build());

    }

    @Override
    public void deleteNotification(int id) {
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }

    @Override
    public void showAlarmClockOff(String hour, String minute) {
        Toast.makeText(MyApplication.getAppContext(),
                "Будильник на " + hour + ":" + minute+ " выключен",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAlarmClockOn(String hour, String minute) {
        Toast.makeText(MyApplication.getAppContext(),
                "Будильник включен на " + hour + ":" + minute,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDeleteDialog(final AlarmClock alarmClock) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Удалить будильник на " + alarmClock.getHour() +
                ":" + alarmClock.getMinute() + "?")
                .setCancelable(false)
                .setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.onDeleteWasClicked(alarmClock);
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    public void createBroadcastReceiver() {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int id = intent.getIntExtra(INTENT_EXTRA, 1);
                presenter.onReceive(id);
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_ON);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private void createBroadcastReceiverOff() {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int id = intent.getIntExtra(INTENT_EXTRA, 1);
                presenter.onReceiveOff(id);
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_OFF);
        registerReceiver(broadcastReceiver, intentFilter);
    }
}
