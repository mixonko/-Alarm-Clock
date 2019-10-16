package com.myapp.test.alarmclock.contract;

import com.myapp.test.alarmclock.entity.AlarmClock;

import java.util.List;

public interface MainContract {
    interface View{
        void setInfoText(String infoText);
        void startCreateActivity();
        void startChangeActivity(int id);
        void alarmClockOn(int hour, int minute, int id);
        void alarmClockOff(int id);
        void showAlarmClockOn(String hour, String minute);
        void showDeleteDialog(AlarmClock alarmClock, int position, String days);
        void deleteItem(int position);
        void updateList(List<AlarmClock> list);
        void setList(List<AlarmClock> list);
    }

    interface Presenter{
        void onActivityCreate();
        void onActivityResume();
        void onItemWasClicked(AlarmClock alarmClock);
        void onItemWasLongClicked(AlarmClock alarmClock, int position);
        void onSwitchWasChanged(Boolean b, AlarmClock alarmClock);
        void onCreateButtonWasClicked();
        void onDeleteWasClicked(AlarmClock alarmClock, int position);
        void onActivityResult(int id);
        void cancelWasReceived();
    }

}
