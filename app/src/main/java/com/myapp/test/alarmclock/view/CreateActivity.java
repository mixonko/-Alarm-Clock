package com.myapp.test.alarmclock.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.myapp.test.alarmclock.R;
import com.myapp.test.alarmclock.contracts.CreateContract;
import com.myapp.test.alarmclock.myAppContext.MyApplication;
import com.myapp.test.alarmclock.presenter.CreatePresenter;
import com.myapp.test.alarmclock.view.adapter.ExampleDaysAdapter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CreateActivity extends AppCompatActivity implements CreateContract.view, View.OnClickListener {

    private CreateContract.presenter presenter;
    private Button close;
    private Button done;
    private TimePicker timePicker;
    private TextView sound, description;
    private Switch vibrationSignal;
    private TextView daysOfWeek;
    private int mMonday = 0;
    private int mTuesday = 0;
    private int mWednesday = 0;
    private int mThursday = 0;
    private int mFriday = 0;
    private int mSaturday = 0;
    private int mSunday = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        presenter = new CreatePresenter(this);

        close = findViewById(R.id.close);
        done = findViewById(R.id.done);
        timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        daysOfWeek = findViewById(R.id.days_of_week);
        sound = findViewById(R.id.sound);
        vibrationSignal = findViewById(R.id.vibration_signal);
        description = findViewById(R.id.description);

        close.setOnClickListener(this);
        done.setOnClickListener(this);
        daysOfWeek.setOnClickListener(this);
        sound.setOnClickListener(this);
        description.setOnClickListener(this);

    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public int getHour() {
        return timePicker.getHour();
    }

    @Override
    public int getMinute() {
        return timePicker.getMinute();
    }

    @Override
    public void showDescriptionDialog() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View v = layoutInflater.inflate(R.layout.description, null, false);
        final EditText et = v.findViewById(R.id.des);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(v);
        builder.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.onDescriptionDone(et.getText().toString());
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).setCancelable(false)
                .show();
    }

    @Override
    public void setDescription(String description) {
        if (description.isEmpty()) {
            this.description.setText(R.string.alarm_clock);
        } else {
            this.description.setText(description);
        }
    }

    @Override
    public void createAlarmClock(int hour, int minute, int id) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }
        Intent intent = new Intent(MainActivity.ACTION_ON);
        intent.putExtra(MainActivity.INTENT_EXTRA, id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getAppContext(),
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }

    @Override
    public Boolean getVibrationInfo() {
        return vibrationSignal.isChecked();
    }

    @Override
    public String getDescription() {
        return description.getText().toString();
    }

    @Override
    public void showAlarmClockOn(String hour, String minute) {
        Toast.makeText(MyApplication.getAppContext(),
                "Будильник включен на " + hour + ":" + minute,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public List<String> getDaysList() {
        String[] s = MyApplication.getAppContext().getResources().getStringArray(R.array.daysOfWeek);
        List<String> list = Arrays.asList(s);
        return list;
    }

    @Override
    public void showDaysDialog(List<String> daysList, List<Integer> checkedDays) {

        RecyclerView recyclerView = new RecyclerView(MyApplication.getAppContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.getAppContext());
        ExampleDaysAdapter adapter = new ExampleDaysAdapter(daysList, checkedDays);

        adapter.setOnItemClickListener(new ExampleDaysAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int monday, int tuesday, int wednesday, int thursday, int friday, int saturday, int sunday) {
                mMonday = monday;
                mTuesday = tuesday;
                mWednesday = wednesday;
                mThursday = thursday;
                mFriday = friday;
                mSaturday = saturday;
                mSunday = sunday;

            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(recyclerView);
        builder.setTitle(R.string.days_of_the_week);
        builder.setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.saveDaysWasClicked(mMonday, mTuesday, mWednesday, mThursday, mFriday, mSaturday, mSunday);
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).setCancelable(false);
        builder.show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                presenter.onCloseWasClicked();
                break;
            case R.id.done:
                presenter.onDoneWasClicked();
                break;
            case R.id.description:
                presenter.onDescriptionWasClicked();
                break;
            case R.id.days_of_week:
                presenter.onDaysWasClicked();
                break;
        }
    }

}
