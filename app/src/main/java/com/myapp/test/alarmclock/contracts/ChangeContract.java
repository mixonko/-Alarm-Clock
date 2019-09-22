package com.myapp.test.alarmclock.contracts;

public interface ChangeContract {
    interface view{
        void close();
        void setHour(int hour);
        void setMinute(int minute);
        void setVibration(Boolean vibration);
        void showDescriptionDialog();
        void setDescription(String description);
        int getHour();
        int getMinute();
        void createAlarmClock(int hour, int minute, int id);
        Boolean getVibrationInfo();
        String getDescription();
        void showAlarmClockOn(String hour, String minute);
    }
    
    interface presenter{ 
        void onCreate(int id);
        void onCloseWasClicked();
        void onDoneWasClicked();
        void onDescriptionWasClicked();
        void onDescriptionDone(String description);
    }
     
}
