package com.myapp.test.alarmclock.contract;

import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.entity.DaysOfWeek;

import java.util.List;

public interface MainContract {
    interface View{
        void startCreateActivity();
        void startChangeActivity(int id);
        Long alarmClockOn(int id, int hour, int minute, DaysOfWeek daysOfWeek);
        void alarmClockOff(int id);
        void showAlarmClockOn(String info);
        void showDeleteDialog(AlarmClock alarmClock, int position, String days);
        void deleteItem(int position);
        void updateList(List<AlarmClock> list);
        void setList(List<AlarmClock> list);
        void setInfoText(String time);
        void showNextClockView();
    }

    interface Presenter{
        void onItemWasClicked(AlarmClock alarmClock);
        void onItemWasLongClicked(AlarmClock alarmClock, int position);
        void onSwitchWasChanged(Boolean b, AlarmClock alarmClock);
        void onCreateButtonWasClicked();
        void onDeleteWasClicked(AlarmClock alarmClock, int position);
        void onActivityResult(int id);
        void updateWasReceived();
        void onActivityResume();
        void onImgSwitcherWasClicked();
    }

}
