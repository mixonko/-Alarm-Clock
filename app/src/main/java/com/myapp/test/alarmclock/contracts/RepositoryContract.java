package com.myapp.test.alarmclock.contracts;

import com.myapp.test.alarmclock.entity.AlarmClock;

import java.util.List;

public interface RepositoryContract{
    void addAlarmClock(AlarmClock alarmClock);
    void deleteAlarmClock(AlarmClock alarmClock);
    void updateAlarmClock(AlarmClock alarmClock);
    List<AlarmClock> getAllAlarmClock();
    AlarmClock getAlarmClock(String time);

}
