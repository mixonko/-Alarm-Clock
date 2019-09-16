package com.myapp.test.alarmclock.contracts;

public interface CreateContract {
    interface view {
        void close();
        int getHour();
        int getMinute();
        void setDays();
        void setSound();
        void setVibration();
        void setDescription();
        void startAlarmClock(int hour, int minute);
    }

    interface presenter{
        void onCloseWasClicked();
        void onDoneWasClicked();
        void onDaysOfTheWeekWasClicked();
        void onSoundWasClicked();
        void onVibrationSignalWasClicked();
        void onDescriptionWasClicked();
    }

}
