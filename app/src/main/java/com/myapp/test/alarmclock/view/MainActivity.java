package com.myapp.test.alarmclock.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.test.alarmclock.R;
import com.myapp.test.alarmclock.contract.MainContract;
import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.entity.DaysOfWeek;
import com.myapp.test.alarmclock.myAppContext.MyApplication;
import com.myapp.test.alarmclock.presenter.MainPresenter;
import com.myapp.test.alarmclock.view.adapter.ExampleAdapter;
import com.myapp.test.alarmclock.view.other.ClockView;

import java.util.ArrayList;
import java.util.List;

import static com.myapp.test.alarmclock.other.RegisterAlarmClock.registerAlarmClock;
import static com.myapp.test.alarmclock.other.RegisterAlarmClock.unRegisterAlarmClock;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private MainContract.Presenter presenter;
    private Button create;
    private TextView infoText;
    private RecyclerView recyclerView;
    private ExampleAdapter exampleAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<AlarmClock> list = new ArrayList<>();
    private BroadcastReceiver updateAlarmClockReceiver;
    public static final String INTENT_EXTRA = "INTENT_EXTRA";
    public static final String ALARM_CLOCK_ID = "ALARM_CLOCK_ID";
    public static final String UPDATE = "UPDATE";
    public static final String RESULT_ID = "RESULT_ID";
    public static final int RESULT_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.alarm_clock);

        list.add(new AlarmClock("Asd", "ASdasd",0, true,true, "dsad", new DaysOfWeek(0,0,0,0,0,0,0), "sadasd","Asdasd", "asd"));

        presenter = new MainPresenter(this);

        create = findViewById(R.id.create);
        infoText = findViewById(R.id.info_text);
        recyclerView = findViewById(R.id.list);
        linearLayoutManager = new LinearLayoutManager(MyApplication.getAppContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onCreateButtonWasClicked();
            }
        });

        presenter.onActivityCreate();
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
    public void setInfoText(String time) {
        infoText.setText(time);
    }

    @Override
    public void startCreateActivity() {
        startActivityForResult(new Intent(MyApplication.getAppContext(), CreateActivity.class),
                RESULT_REQUEST_CODE);
    }

    @Override
    public void startChangeActivity(int id) {
        Intent intent = new Intent(MyApplication.getAppContext(), ChangeActivity.class);
        intent.putExtra(ALARM_CLOCK_ID, id);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    @Override
    public Long alarmClockOn(int id, int hour, int minute, DaysOfWeek daysOfWeek) {
        return registerAlarmClock(id, hour, minute, daysOfWeek);
    }

    @Override
    public void alarmClockOff(int id) {
        unRegisterAlarmClock(id);
    }

    @Override
    public void showAlarmClockOn(String info) {
        Toast.makeText(MyApplication.getAppContext(), info, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDeleteDialog(final AlarmClock alarmClock, final int position, String days) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Удалить будильник на " + alarmClock.getHour() +
                ":" + alarmClock.getMinute() + " (" + days.trim() + ")" + " ?")
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == RESULT_REQUEST_CODE) {
            int id = data.getIntExtra(RESULT_ID, 1);
            presenter.onActivityResult(id);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
            updateAlarmClockReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    presenter.updateWasReceived();
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(UPDATE);
            registerReceiver(updateAlarmClockReceiver, intentFilter);
            presenter.onActivityResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(updateAlarmClockReceiver);
    }
}
