package com.myapp.test.alarmclock.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.myapp.test.alarmclock.R;
import com.myapp.test.alarmclock.contracts.CreateContract;
import com.myapp.test.alarmclock.myAppContext.MyApplication;
import com.myapp.test.alarmclock.presenter.CreatePresenter;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CreateActivity extends AppCompatActivity implements CreateContract.view, View.OnClickListener {

    private CreateContract.presenter presenter;
    private Button close;
    private Button done;
    private EditText hour;
    private EditText minute;
    private List days;
    private Boolean vibration;
    private String des;
    private FrameLayout daysOfTheWeek, sound, vibrationSignal, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        presenter = new CreatePresenter(this);

        close = findViewById(R.id.close);
        done = findViewById(R.id.done);
        hour = findViewById(R.id.hour);
        minute = findViewById(R.id.minute);
        daysOfTheWeek = findViewById(R.id.days_of_the_week);
        sound = findViewById(R.id.sound);
        vibrationSignal = findViewById(R.id.vibration_signal);
        description = findViewById(R.id.description);

        close.setOnClickListener(this);
        done.setOnClickListener(this);
        daysOfTheWeek.setOnClickListener(this);
        sound.setOnClickListener(this);
        vibrationSignal.setOnClickListener(this);
        description.setOnClickListener(this);
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public String getHour() {
        return hour.getText().toString();
    }

    @Override
    public String getMinute() {
        return minute.getText().toString();
    }

    @Override
    public void setDays() {

    }

    @Override
    public void setSound() {

    }

    @Override
    public void setVibration() {

    }

    @Override
    public void setDescription() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View v = layoutInflater.inflate(R.layout.description, null, false);
        final EditText et = v.findViewById(R.id.des);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(v);
        builder.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                des = String.valueOf(et.getText());
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                presenter.onCloseWasClicked();
                break;
            case R.id.done:
                presenter.onDoneWasClicked();
                break;
            case R.id.days_of_the_week:
                presenter.onDaysOfTheWeekWasClicked();
                break;
            case R.id.sound:
                presenter.onSoundWasClicked();
                break;
            case R.id.vibration_signal:
                presenter.onVibrationSignalWasClicked();
                break;
            case R.id.description:
                presenter.onDescriptionWasClicked();
                break;
        }
    }
}
