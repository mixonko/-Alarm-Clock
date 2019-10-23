package com.myapp.test.alarmclock.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.myapp.test.alarmclock.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.myapp.test.alarmclock.view.MyNotification.ID_EXTRA;
import static com.myapp.test.alarmclock.view.MyNotification.TEXT_EXTRA;
import static com.myapp.test.alarmclock.view.MyNotification.TITLE_EXTRA;
import static com.myapp.test.alarmclock.view.MyNotification.getMyNotification;

public class AlarmActivity extends AppCompatActivity {
    private TextView title, text;
    private com.ebanx.swipebtn.SwipeButton alarmOff;
    private int id;
    private String titleText, timeText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        title = findViewById(R.id.title);
        text = findViewById(R.id.text);
        alarmOff = findViewById(R.id.alarm_off);

        id = getIntent().getIntExtra(ID_EXTRA, 1);
        timeText = getIntent().getStringExtra(TEXT_EXTRA);
        titleText = getIntent().getStringExtra(TITLE_EXTRA);

        text.setText(timeText);
        title.setText(titleText);

        alarmOff.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                getMyNotification().deleteNotification(id);
                getMyNotification().stopRingtone();
                getMyNotification().stopVibration();
                finish();
            }
        });

    }
}
