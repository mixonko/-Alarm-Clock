package com.myapp.test.alarmclock.contracts;

import com.myapp.test.alarmclock.entity.AlarmClock;

import java.util.List;

public interface MainContract {
    interface View{
        void setInfoText(String infoText);
        void startCreateActivity();
        void startCreateActivity(AlarmClock alarmClock);
        void updateList();
        void setAdapter(List<AlarmClock> list);
    }

    interface Presenter{
        void onCreateActivity();
        void onItemWasClicked(int position);
        void onCreateButtonWasClicked();
        void onSwitchWasClicked(int position);
    }

}
