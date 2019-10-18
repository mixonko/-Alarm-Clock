package com.myapp.test.alarmclock.contract;

import com.myapp.test.alarmclock.entity.AlarmClock;

import java.util.List;

public interface RepositoryContract{
    void addAlarmClock(AlarmClock alarmClock);
    void deleteAlarmClock(AlarmClock alarmClock);
    void updateAlarmClock(AlarmClock alarmClock);
    List<AlarmClock> getAllAlarmClocks();
    AlarmClock getAlarmClock(int id);
    List<Long> getWorkingAlarms(Boolean b) throws Exception;
}
