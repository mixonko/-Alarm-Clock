package com.myapp.test.alarmclock.permission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import com.myapp.test.alarmclock.myAppContext.MyApplication;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class CheckReadStoragePermission {
    public static boolean checkSelfPermission(@NonNull Context context) {
        return ActivityCompat.checkSelfPermission(MyApplication.getAppContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ;
    }
}
