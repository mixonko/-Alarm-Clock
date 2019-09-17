package com.myapp.test.alarmclock.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.myapp.test.alarmclock.AlarmReceiver;
import com.myapp.test.alarmclock.R;
import com.myapp.test.alarmclock.contracts.MainContract;
import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.myAppContext.MyApplication;
import com.myapp.test.alarmclock.presenter.MainPresenter;
import com.myapp.test.alarmclock.view.adapter.ExampleAdapter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View{
    private static final String BUNDLE_KEY = "bundle_key";
    private static final String ALARM_CLOCK_KEY = "alarm_clock_key";
    private MainContract.Presenter presenter;
    private Button create;
    private RecyclerView recyclerView;
    private ExampleAdapter exampleAdapter;
    private LinearLayoutManager linearLayoutManager;

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

    }

    @Override
    public void setInfoText(String infoText) {

    }

    @Override
    public void startCreateActivity() {
        startActivity(new Intent(MyApplication.getAppContext(), CreateActivity.class));
    }

    @Override
    public void startCreateActivity(AlarmClock alarmClock) {
        Intent intent = new Intent(MyApplication.getAppContext(), CreateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_KEY, (Serializable) alarmClock);
        intent.putExtra(ALARM_CLOCK_KEY, bundle);
        startActivityForResult(intent, 1);
    }

    @Override
    public void updateList() {
        exampleAdapter.notifyDataSetChanged();
    }

    @Override
    public void setAdapter(final List<AlarmClock> list) {
        if (list.size() != 0) {
            exampleAdapter = new ExampleAdapter(list);
            recyclerView.setAdapter(exampleAdapter);
            exampleAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    presenter.onItemWasClicked(position);
                }
            });
            exampleAdapter.setOnCheckedChangeListener(new ExampleAdapter.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(int position, CompoundButton compoundButton, boolean b) {
                    AlarmClock alarmClock = list.get(position);
                    presenter.onSwitchWasChanged(position, b, alarmClock);
                }
            });
        }

    }

    @Override
    public void alarmClockOn(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        if(calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }
        Intent intent = new Intent(MyApplication.getAppContext(), AlarmReceiver.class);
//        intent.putExtra("extra", id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getAppContext(),
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void alarmClockOff() {
        Intent intent = new Intent(MyApplication.getAppContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getAppContext(),
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

}
