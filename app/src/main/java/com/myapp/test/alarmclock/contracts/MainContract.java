package com.myapp.test.alarmclock.contracts;

import com.myapp.test.alarmclock.entity.AlarmClock;

import java.util.List;

public interface MainContract {
    interface View{
        void setInfoText(String infoText);
        void startCreateActivity();
        void startCreateActivity(AlarmClock alarmClock);
        void setAdapter(List<AlarmClock> list);
        void alarmClockOn(int hour, int minute, int id);
        void alarmClockOff(int id);
        void createNotification(int id, AlarmClock alarmClock);
        void deleteNotification(int id);
        void showAlarmClockOff(String hour, String minute);
        void showAlarmClockOn(String hour, String minute);

    }

    interface Presenter{
        void onCreateActivity();
        void onItemWasClicked(int position);
        void onCreateButtonWasClicked();
        void onSwitchWasChanged(int position, Boolean b, AlarmClock alarmClock);
        void onResume();
        void onReceive(int id);
        void onReceiveOff(int id);
    }

}
