package com.myapp.test.alarmclock.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.myapp.test.alarmclock.R;
import com.myapp.test.alarmclock.contract.ChangeContract;
import com.myapp.test.alarmclock.myAppContext.MyApplication;
import com.myapp.test.alarmclock.presenter.ChangePresenter;
import com.myapp.test.alarmclock.view.adapter.ExampleDaysAdapter;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.myapp.test.alarmclock.view.MainActivity.RESULT_ID;

public class ChangeActivity extends AppCompatActivity implements ChangeContract.view, View.OnClickListener {
    private ChangeContract.presenter presenter;
    private static final int REQUEST_CODE_RINGTONE = 5;
    private Button close;
    private Button done;
    private TimePicker timePicker;
    private TextView sound, description;
    private Switch vibrationSignal;
    private TextView daysOfWeek;
    private String ringtonePath;
    private String ringtoneName;
    private String pickedDays;
    private int mMonday = 0;
    private int mTuesday = 0;
    private int mWednesday = 0;
    private int mThursday = 0;
    private int mFriday = 0;
    private int mSaturday = 0;
    private int mSunday = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        presenter = new ChangePresenter(this);

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

        Intent intent = getIntent();
        int id = intent.getIntExtra(MainActivity.ALARM_CLOCK_ID, 1);

        pickedDays = MyApplication.getAppContext().getString(R.string.without_replay);
        presenter.onActivityCreate(id);
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
            case R.id.sound:
                presenter.onRingtonesWasClicked();
                break;
        }
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void setHour(int hour) {
        timePicker.setHour(hour);
    }

    @Override
    public void setMinute(int minute) {
        timePicker.setMinute(minute);
    }

    @Override
    public void setVibration(Boolean vibration) {
        vibrationSignal.setChecked(vibration);
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
        this.description.setText(description);
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
    public Boolean getVibrationInfo() {
        return vibrationSignal.isChecked();
    }

    @Override
    public String getDescription() {
        return description.getText().toString();
    }

    @Override
    public void showDaysDialog(List<String>daysList, List<Integer>checkedDays) {
        RecyclerView recyclerView = new RecyclerView(MyApplication.getAppContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.getAppContext());
        ExampleDaysAdapter adapter = new ExampleDaysAdapter(daysList, checkedDays);

        adapter.setOnItemClickListener(new ExampleDaysAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int monday, int tuesday, int wednesday, int thursday, int friday, int saturday, int sunday, String days) {
                mMonday = monday;
                mTuesday = tuesday;
                mWednesday = wednesday;
                mThursday = thursday;
                mFriday = friday;
                mSaturday = saturday;
                mSunday = sunday;
                pickedDays = days;
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
                presenter.saveDaysWasClicked(mMonday, mTuesday, mWednesday, mThursday, mFriday, mSaturday, mSunday );
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
    public List<String> getDaysList() {
        String[] s = MyApplication.getAppContext().getResources().getStringArray(R.array.daysOfWeek);
        List<String> list = Arrays.asList(s);
        return list;
    }

    @Override
    public void setRingtonePath(String ringtoneUri) {
        ringtonePath = ringtoneUri;
    }

    @Override
    public String getRingtonePath() {
        return ringtonePath;
    }

    @Override
    public String getPickedDaysText() {
        return pickedDays;
    }

    @Override
    public void setPickedDaysText(String daysOfWeekText) {
            daysOfWeek.setText(daysOfWeekText);
    }

    @Override
    public void showRingtones() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
        this.startActivityForResult(intent, REQUEST_CODE_RINGTONE);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        if (resultCode == Activity.RESULT_OK && requestCode == 5) {
            Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if (uri != null) {
                ringtonePath = uri.toString();
                ringtoneName = getRingtoneName(uri);
            }
        }
    }

    private String getRingtoneName(Uri uri){
        String fileName = "";
        if (uri.getScheme().equals("file")) {
            fileName = uri.getLastPathSegment();
        } else {
            Cursor cursor = null;
            try {
                cursor = getContentResolver().query(uri, new String[]{
                        MediaStore.Images.ImageColumns.DISPLAY_NAME
                }, null, null, null);

                if (cursor != null && cursor.moveToFirst()) {
                    fileName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));
                }
            } finally {

                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return  fileName;
    }

    @Override
    public String getRingtoneName(){
        return ringtoneName;
    }

    @Override
    public void setRingtoneName(String ringtoneName) {

    }

    @Override
    public void setActivityResult(int id) {
        Intent intent = new Intent();
        intent.putExtra(RESULT_ID, id);
        setResult(RESULT_OK, intent);
    }
}
