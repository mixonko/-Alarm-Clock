package com.myapp.test.alarmclock.contract;

import com.myapp.test.alarmclock.entity.DaysOfWeek;

import java.util.List;

public interface CreateContract {
    interface view {
        void close();
        int getHour();
        int getMinute();
        void showDescriptionDialog();
        void setDescriptionText(String description);
        Boolean getVibrationInfo();
        String getDescription();
        List<String> getDaysList();
        void showDaysDialog(List<String>daysList, DaysOfWeek daysOfWeek);
        void showRingtones();
        String getRingtonePath();
        void setActivityResult(int id);
        String getPickedDaysText();
        void setPickedDaysText(String daysOfWeekText);
        String getRingtoneName();
        void setRingtoneNameText(String ringtoneName);
        void setVibration(Boolean vibration);
        DaysOfWeek getDaysOfWeek();
    }

    interface presenter{
        void onDoneWasClicked();
        void onDescriptionWasClicked();
        void onDescriptionDone(String description);
        void onDaysWasClicked();
        void saveDaysWasClicked();
        void onRingtonesWasClicked();
        void onRingtoneResult(String ringtoneName);
        void onVibrationWasClicked();

    }

}
