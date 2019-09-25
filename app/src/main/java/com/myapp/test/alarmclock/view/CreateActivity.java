package com.myapp.test.alarmclock.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.myapp.test.alarmclock.R;
import com.myapp.test.alarmclock.contracts.CreateContract;
import com.myapp.test.alarmclock.myAppContext.MyApplication;
import com.myapp.test.alarmclock.presenter.CreatePresenter;

import java.util.Calendar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CreateActivity extends AppCompatActivity implements CreateContract.view, View.OnClickListener {

    private CreateContract.presenter presenter;
    private Button close;
    private Button done;
    private TimePicker timePicker;
    private TextView sound, description;
    private Switch vibrationSignal;
    private TextView daysOfWeek;

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
        if (description.isEmpty()){
            this.description.setText(R.string.alarm_clock);
        }else{
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
        }
    }

}
