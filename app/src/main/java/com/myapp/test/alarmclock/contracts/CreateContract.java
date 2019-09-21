package com.myapp.test.alarmclock.contracts;

public interface CreateContract {
    interface view {
        void close();
        int getHour();
        int getMinute();
        void setDescription();
        void createAlarmClock(int hour, int minute, int id);
        Boolean getVibrationInfo();
        String getDescription();
        void showAlarmClockOn(String hour, String minute);
    }

    interface presenter{
        void onCloseWasClicked();
        void onDoneWasClicked();
        void onDescriptionWasClicked();

    }

}
