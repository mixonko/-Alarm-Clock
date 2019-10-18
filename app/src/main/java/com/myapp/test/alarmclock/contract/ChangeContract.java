package com.myapp.test.alarmclock.contract;

import com.myapp.test.alarmclock.entity.DaysOfWeek;

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
        String getRingtonePath();
        String getRingtoneName();
        void setRingtoneNameText(String ringtoneName);
        void showRingtones();
        void setActivityResult(int id);
        long getTimeInMillis(int hour, int minute);
        String getPickedDaysText();
        void setPickedDaysText(String daysOfWeekText);
        void setDaysOfWeek(DaysOfWeek daysOfWeek);
        DaysOfWeek getDaysOfWeek();

    }
    
    interface presenter{ 
        void onActivityCreate(int id);
        void onDoneWasClicked();
        void onDescriptionWasClicked();
        void onDescriptionDone(String description);
        void onDaysWasClicked();
        void saveDaysWasClicked();
        void onRingtonesWasClicked();
        void onVibrationWasClicked();
        void onRingtoneResult(String ringtoneName);
    }
     
}
