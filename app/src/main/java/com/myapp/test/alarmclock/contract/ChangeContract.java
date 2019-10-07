package com.myapp.test.alarmclock.contract;

import java.util.List;

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
        void showDaysDialog(List<String>daysList, List<Integer>checkedDays);
        List<String> getDaysList();
        void setRingtone(String ringtoneUri);
        String getRingtone();
        void showRingtones();
        void setResult();
    }
    
    interface presenter{ 
        void onCreate(int id);
        void onCloseWasClicked();
        void onDoneWasClicked();
        void onDescriptionWasClicked();
        void onDescriptionDone(String description);
        void onDaysWasClicked();
        void saveDaysWasClicked(int monday, int tuesday, int wednesday, int thursday, int friday, int saturday, int sunday);
        void onRingtonesWasClicked();
    }
     
}
