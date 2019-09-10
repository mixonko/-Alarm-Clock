package com.myapp.test.alarmclock.database;

import android.content.Context;

import com.myapp.test.alarmclock.dao.AlarmClockDao;
import com.myapp.test.alarmclock.entity.AlarmClock;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

//@Database(entities = AlarmClock.class, version = 1)
public abstract class MyAppDatabase extends RoomDatabase {

    public static MyAppDatabase INSTANCE;

    public abstract AlarmClockDao alarmClockDao();

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {
    }

    public static MyAppDatabase getDatabase(Context context) {

        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context,
                            MyAppDatabase.class, "alarm_clock_database")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();

        }
        return INSTANCE;

    }
}
