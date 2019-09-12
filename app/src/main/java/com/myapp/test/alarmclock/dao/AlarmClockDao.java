package com.myapp.test.alarmclock.dao;

import com.myapp.test.alarmclock.entity.AlarmClock;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AlarmClockDao {

    @Insert
    public void addAlarmClock(AlarmClock alarmClock);

    @Delete
    public void deleteAlarmClock(AlarmClock alarmClock);

    @Update
    public void updateAlarmClock(AlarmClock alarmClock);

    @Query("select * from alarm_clock")
    public List<AlarmClock> getAllAlarmClock();

    @Query("select * from alarm_clock where hour = :hour")
    public AlarmClock getAlarmClock(String hour);

}
