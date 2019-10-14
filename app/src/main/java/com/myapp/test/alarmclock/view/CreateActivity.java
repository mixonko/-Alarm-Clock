package com.myapp.test.alarmclock.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.myapp.test.alarmclock.R;
import com.myapp.test.alarmclock.contract.CreateContract;
import com.myapp.test.alarmclock.myAppContext.MyApplication;
import com.myapp.test.alarmclock.permission.CheckReadStoragePermission;
import com.myapp.test.alarmclock.presenter.CreatePresenter;
import com.myapp.test.alarmclock.view.adapter.ExampleDaysAdapter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.myapp.test.alarmclock.view.MainActivity.RESULT_ID;

public class CreateActivity extends AppCompatActivity implements CreateContract.view, View.OnClickListener {

    private static final int REQUEST_CODE_RINGTONE = 5;
    private CreateContract.presenter presenter;
    private TimePicker timePicker;
    private FrameLayout soundLayout, descriptionLayout, daysLayout, vibrationLayout;
    private TextView ringtoneText, description, days;
    private androidx.appcompat.widget.SwitchCompat vibrationSignal;
    private int mMonday = 0;
    private int mTuesday = 0;
    private int mWednesday = 0;
    private int mThursday = 0;
    private int mFriday = 0;
    private int mSaturday = 0;
    private int mSunday = 0;
    private String pickedDays;
    private String ringtonePath;
    private String ringtoneName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.alarm_clock);

        presenter = new CreatePresenter(this);

        timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        daysLayout = findViewById(R.id.days_of_week);
        days = findViewById(R.id.days);
        soundLayout = findViewById(R.id.sound_layout);
        ringtoneText = findViewById(R.id.sound);
        vibrationLayout = findViewById(R.id.vibration_layout);
        vibrationSignal = findViewById(R.id.vibration);
        descriptionLayout = findViewById(R.id.description_layout);
        description = findViewById(R.id.description);

        daysLayout.setOnClickListener(this);
        soundLayout.setOnClickListener(this);
        vibrationLayout.setOnClickListener(this);
        descriptionLayout.setOnClickListener(this);

        ringtonePath = RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI;
        ringtoneName = MyApplication.getAppContext().getString(R.string.defauly_ringtone);
        pickedDays = MyApplication.getAppContext().getString(R.string.without_replay);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                presenter.onDoneWasClicked();
                break;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
    public Boolean getVibrationInfo() {
        return vibrationSignal.isChecked();
    }

    @Override
    public String getDescription() {
        return description.getText().toString();
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
            case R.id.description_layout:
                presenter.onDescriptionWasClicked();
                break;
            case R.id.days_of_week:
                presenter.onDaysWasClicked();
                break;
            case R.id.vibration_layout:
                 presenter.onVibrationWasClicked();
                break;
            case R.id.sound_layout:
                if (CheckReadStoragePermission.checkSelfPermission(MyApplication.getAppContext())){
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            1);
                }else {
                    presenter.onRingtonesWasClicked();
                }
                break;
        }
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
                presenter.onRingtoneResult(ringtoneName);
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
    public void setRingtoneNameText(String ringtoneName) {
        ringtoneText.setText(ringtoneName);
    }

    @Override
    public void setVibration(Boolean vibration) {
        vibrationSignal.setChecked(vibration);
    }

    @Override
    public String getRingtonePath() {
        return ringtonePath;
    }

    @SuppressLint( "WrongConstant" )
    @Override
    public long getTimeInMillis(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
//        if (calendar.before(Calendar.getInstance())) {
//            calendar.add(Calendar.DATE, 1);
//        }
        return calendar.getTimeInMillis();
    }

    @Override
    public String getPickedDays() {
        return pickedDays;
    }

    @Override
    public void setPickedDaysText(String daysOfWeekText) {
        days.setText(daysOfWeekText);
    }

    @Override
    public void setActivityResult(int id) {
        Intent intent = new Intent();
        intent.putExtra(RESULT_ID, id);
        setResult(RESULT_OK, intent);
    }

}
