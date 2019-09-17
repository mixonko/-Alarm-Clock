package com.myapp.test.alarmclock.contracts;

public interface CreateContract {
    interface view {
        void close();
        int getHour();
        int getMinute();
        void setDescription();
        void createAlarmClock(int hour, int minute);
    }

    interface presenter{
        void onCloseWasClicked();
        void onDoneWasClicked();
        void onDescriptionWasClicked();
    }

}
