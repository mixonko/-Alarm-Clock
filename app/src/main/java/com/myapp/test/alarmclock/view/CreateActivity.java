package com.myapp.test.alarmclock.view;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.myapp.test.alarmclock.R;
import com.myapp.test.alarmclock.contract.CreateContract;
import com.myapp.test.alarmclock.entity.DaysOfWeek;
import com.myapp.test.alarmclock.myAppContext.MyApplication;
import com.myapp.test.alarmclock.other.Permissions;
import com.myapp.test.alarmclock.permission.CheckReadStoragePermission;
import com.myapp.test.alarmclock.presenter.CreatePresenter;
import com.myapp.test.alarmclock.view.adapter.ExampleDaysAdapter;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
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
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int LOAD_IMAGE_REQUEST_CODE = 2;
    private CreateContract.presenter presenter;
    private TimePicker timePicker;
    private FrameLayout soundLayout, descriptionLayout, daysLayout, vibrationLayout, photoLayout, photo;
    private TextView ringtoneText, description, days;
    private androidx.appcompat.widget.SwitchCompat vibrationSignal, photoSwitch;
    private DaysOfWeek mDaysOfWeek;
    private String mPickedDaysText;
    private String ringtonePath;
    private String ringtoneName;
    private Button photoButton, galleryButton;
    private ImageView photoImage;
    private LinearLayout.LayoutParams params;

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
        photoLayout = findViewById(R.id.photo_layout);
        description = findViewById(R.id.description);
        photoSwitch = findViewById(R.id.photo_switch);
        photoButton = findViewById(R.id.photo_button);
        galleryButton = findViewById(R.id.gallery);
        photoImage = findViewById(R.id.photo_image);
        photo = findViewById(R.id.photo);

        daysLayout.setOnClickListener(this);
        soundLayout.setOnClickListener(this);
        vibrationLayout.setOnClickListener(this);
        descriptionLayout.setOnClickListener(this);
        photoLayout.setOnClickListener(this);
        photoButton.setOnClickListener(this);
        galleryButton.setOnClickListener(this);

        photoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                presenter.onCheckedChanged(b);
            }
        });

        ringtonePath = RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI;
        ringtoneName = MyApplication.getAppContext().getString(R.string.defauly_ringtone);
        mPickedDaysText = MyApplication.getAppContext().getString(R.string.without_replay);
        mDaysOfWeek = new DaysOfWeek(0, 0, 0, 0, 0, 0, 0);

        params = (LinearLayout.LayoutParams) photo.getLayoutParams();
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
        et.setText(description.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.description);
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
    public void setDescriptionText(String description) {
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
    public void showDaysDialog(List<String> daysList, DaysOfWeek daysOfWeek) {
        RecyclerView recyclerView = new RecyclerView(MyApplication.getAppContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.getAppContext());
        final ExampleDaysAdapter adapter = new ExampleDaysAdapter(daysList, daysOfWeek);
        adapter.setOnItemClickListener(new ExampleDaysAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(DaysOfWeek daysOfWeek, String pickedDaysText) {
                mDaysOfWeek = daysOfWeek;
                mPickedDaysText = pickedDaysText;
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
                adapter.clearData();
                presenter.saveDaysWasClicked();

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
                if (CheckReadStoragePermission.checkSelfPermission(MyApplication.getAppContext())) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            1);
                } else {
                    presenter.onRingtonesWasClicked();
                }
                break;
            case R.id.photo_layout:
                presenter.onPhotoCheckWasClicked();
                break;
            case R.id.photo_button:
                presenter.onPhotoButWasClicked();
                break;
            case R.id.gallery:
                presenter.onGalleryWasClicked();
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
        super.onActivityResult(requestCode, resultCode, intent);

        if (intent != null) {
            if (resultCode == Activity.RESULT_OK) {
                switch (requestCode) {
                    case REQUEST_CODE_RINGTONE:
                        if (resultCode == RESULT_OK) {
                            Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

                            if (uri != null) {
                                ringtonePath = uri.toString();
                                ringtoneName = getRingtoneName(uri);
                                presenter.onRingtoneResult(ringtoneName);
                            }
                        }
                        break;
                    case LOAD_IMAGE_REQUEST_CODE:
                        if (resultCode == RESULT_OK) {
                            Uri pickedImage = intent.getData();
                            photoImage.setImageURI(pickedImage);
                        }
                        break;
                    case REQUEST_IMAGE_CAPTURE:
                        if (resultCode == RESULT_OK) {
                            Bundle extras = intent.getExtras();
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            Uri pickedImage = (Uri) extras.get("data");
                            photoImage.setImageURI(pickedImage);


                        }
                        break;
                }

            }
        }
    }

    private String getRingtoneName(Uri uri) {
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
        return fileName;
    }

    @Override
    public String getRingtoneName() {
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

    @Override
    public DaysOfWeek getDaysOfWeek() {
        return mDaysOfWeek;
    }

    @Override
    public void startCamera() {
        if (Permissions.checkCameraHardware(MyApplication.getAppContext())) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    1);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }
    }

    @Override
    public void pickImage() {
        if (Permissions.checkStoragePermission(MyApplication.getAppContext())) {
            Intent intentGallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentGallery, LOAD_IMAGE_REQUEST_CODE);
        } else getPermission();
    }

    public void getPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

    @Override
    public void showPhotoLayout() {
        params.height = 150;
        photo.setLayoutParams(params);
    }

    @Override
    public void hidePhotoLayout() {
        params.height = 0;
        photo.setLayoutParams(params);
    }

    @Override
    public Boolean getPhotoCheck() {
        return photoSwitch.isChecked();
    }

    @Override
    public void setPhotoCheck(Boolean b) {
        photoSwitch.setChecked(b);
    }

    @Override
    public String getPickedDaysText() {
        return mPickedDaysText;
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
