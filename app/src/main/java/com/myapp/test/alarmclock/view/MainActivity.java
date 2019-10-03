package com.myapp.test.alarmclock.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.myapp.test.alarmclock.receivers.AlarmClockReceiver;
import com.myapp.test.alarmclock.view.adapter.ExampleAdapter;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private MainContract.Presenter presenter;
    private Button create;
    private RecyclerView recyclerView;
    private ExampleAdapter exampleAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<AlarmClock> list;
    public static final String ACTION_ON = "com.myapp.test.alarmclock.action_on";
    public static final String INTENT_EXTRA = "extra";
    public static final String ALARM_CLOCK_ID = "alarm_clock_id";

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
        exampleAdapter = new ExampleAdapter(this.list);
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
                presenter.onItemWasLongClicked(alarmClock, position);
            }
        });
        exampleAdapter.setOnCheckedChangeListener(new ExampleAdapter.OnChangeListener() {
            @Override
            public void onChangedListener(int position, boolean b) {
                AlarmClock alarmClock = list.get(position);
                presenter.onSwitchWasChanged(b, alarmClock);
            }
        });
    }

    @Override
    public void setList(List<AlarmClock> list) {
        this.list = list;
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
        Intent intent = new Intent(MyApplication.getAppContext(), ChangeActivity.class);
        intent.putExtra(ALARM_CLOCK_ID, id);
        startActivity(intent);
    }

    @Override
    public void alarmClockOn(int hour, int minute, int id) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
// if(calendar.before(Calendar.getInstance())) {
// calendar.add(Calendar.DATE, 1);
// }
        Intent intent = new Intent(MyApplication.getAppContext(), AlarmClockReceiver.class);
        intent.addFlags(Intent.FLAG_RECEIVER_NO_ABORT);
        intent.putExtra(INTENT_EXTRA, id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getAppContext(),
                id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void alarmClockOff(int id) {
        Intent intent = new Intent(MyApplication.getAppContext(), AlarmClockReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getAppContext(),
                id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    @Override
    public void showAlarmClockOff(String hour, String minute) {
        Toast.makeText(MyApplication.getAppContext(),
                "Будильник на " + hour + ":" + minute + " выключен",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAlarmClockOn(String hour, String minute) {
        Toast.makeText(MyApplication.getAppContext(),
                "Будильник включен на " + hour + ":" + minute,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDeleteDialog(final AlarmClock alarmClock, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Удалить будильник на " + alarmClock.getHour() +
                ":" + alarmClock.getMinute() + "?")
                .setCancelable(false)
                .setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.onDeleteWasClicked(alarmClock, position);
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
    public void deleteItem(int position) {
        list.remove(position);
        recyclerView.removeViewAt(position);
        exampleAdapter.notifyItemRemoved(position);
        exampleAdapter.notifyItemRangeChanged(position, list.size());
    }

    @Override
    public void updateList(List<AlarmClock> list) {
        this.list.clear();
        this.list.addAll(list);
        exampleAdapter.updateData(list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

}
