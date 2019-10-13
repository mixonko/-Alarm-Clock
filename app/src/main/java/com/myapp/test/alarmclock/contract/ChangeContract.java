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
        Boolean getVibrationInfo();
        String getDescription();
        void showDaysDialog(List<String>daysList, List<Integer>checkedDays);
        List<String> getDaysList();
        void setRingtonePath(String ringtoneUri);
        String getRingtonePath();
        String getRingtoneName();
        void setRingtoneName(String ringtoneName);
        void showRingtones();
        void setActivityResult(int id);
        String getPickedDaysText();
        void setPickedDaysText(String daysOfWeekText);
    }
    
    interface presenter{ 
        void onActivityCreate(int id);
        void onDoneWasClicked();
        void onDescriptionWasClicked();
        void onDescriptionDone(String description);
        void onDaysWasClicked();
        void saveDaysWasClicked(int monday, int tuesday, int wednesday, int thursday, int friday, int saturday, int sunday);
        void onRingtonesWasClicked();
    }
     
}
