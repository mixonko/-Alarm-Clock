package com.myapp.test.alarmclock.contracts;

public interface CreateContract {
    interface view {
        void close();
        String getHour();
        String getMinute();
        void setDays();
        void setSound();
        void setVibration();
        void setDescription();
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
