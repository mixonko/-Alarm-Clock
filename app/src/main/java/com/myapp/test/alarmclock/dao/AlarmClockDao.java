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
    void addAlarmClock(AlarmClock alarmClock);

    @Delete
    void deleteAlarmClock(AlarmClock alarmClock);

    @Update
    void updateAlarmClock(AlarmClock alarmClock);

    @Query("select * from alarm_clock")
    List<AlarmClock> getAllAlarmClock();

    @Query("select * from alarm_clock where id = :id")
    AlarmClock getAlarmClock(int id);

    @Query("select timeInMillis from alarm_clock where alarm_clock_on = :alarmClockOn ORDER BY timeInMillis")
    List<Long> getSortByTimeInMillis(Boolean alarmClockOn);

}
