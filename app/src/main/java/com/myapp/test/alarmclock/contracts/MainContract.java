package com.myapp.test.alarmclock.contracts;

import com.myapp.test.alarmclock.entity.AlarmClock;

import java.util.List;

public interface MainContract {
    interface View{
        void setInfoText(String infoText);
        void startCreateDialog();
        void updateList();
    }

    interface Presenter{
        void onItemWasClicked(int position);
        void onCreateButtonWasClicked();
        void onSwitchWasClicked();
    }

    interface Repository{
        void addAlarmClock();
        void setAlarmClock();

        interface OnFinishedListener {
            void onFinished(List<AlarmClock> list);
        }
    }
}
