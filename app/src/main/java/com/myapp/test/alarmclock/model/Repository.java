package com.myapp.test.alarmclock.model;

import com.myapp.test.alarmclock.contract.RepositoryContract;
import com.myapp.test.alarmclock.database.MyAppDatabase;
import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.myAppContext.MyApplication;

import java.util.EmptyStackException;
import java.util.List;

public class Repository implements RepositoryContract {
    public static MyAppDatabase database;

    public Repository() {
        database = MyAppDatabase.getDatabase(MyApplication.getAppContext());
    }

    @Override
    public void addAlarmClock(AlarmClock alarmClock) {
        database.alarmClockDao().addAlarmClock(alarmClock);
    }

    @Override
    public void deleteAlarmClock(AlarmClock alarmClock) {
        database.alarmClockDao().deleteAlarmClock(alarmClock);
    }

    @Override
    public void updateAlarmClock(AlarmClock alarmClock) {
        database.alarmClockDao().updateAlarmClock(alarmClock);
    }

    @Override
    public List<AlarmClock> getAllAlarmClocks() {
        return database.alarmClockDao().getAllAlarmClock();
    }

    @Override
    public AlarmClock getAlarmClock(int id) {
        return database.alarmClockDao().getAlarmClock(id);
    }

    @Override
    public List<Long> getWorkingAlarms(Boolean alarmClockOn) throws Exception{
        return database.alarmClockDao().getWorkingAlarms(alarmClockOn);
    }
}
