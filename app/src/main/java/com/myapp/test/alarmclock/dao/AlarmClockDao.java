package com.myapp.test.alarmclock.dao;

import com.myapp.test.alarmclock.entity.AlarmClock;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AlarmClockDao {

    @Insert
    public void addAlarmClock();

    @Query("select * from alarm_clock")
    public List<AlarmClock> getAllCar();

    @Query("select * from alarm_clock where id = :id")
    public AlarmClock getAlarmClock(int id);

}
