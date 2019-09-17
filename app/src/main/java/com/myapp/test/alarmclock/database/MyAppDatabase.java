package com.myapp.test.alarmclock.database;

import android.content.Context;

import com.myapp.test.alarmclock.dao.AlarmClockDao;
import com.myapp.test.alarmclock.entity.AlarmClock;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database( entities = AlarmClock.class, version = 1, exportSchema = false )
public abstract class MyAppDatabase extends RoomDatabase {

    public static MyAppDatabase INSTANCE;

    public abstract AlarmClockDao alarmClockDao();

    public static MyAppDatabase getDatabase(Context context) {

        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, MyAppDatabase.class, "alarmClock_database")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();

        }
        return INSTANCE;
    }



}
