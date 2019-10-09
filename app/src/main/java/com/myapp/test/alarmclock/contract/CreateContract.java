package com.myapp.test.alarmclock.contract;

import java.util.List;

public interface CreateContract {
    interface view {
        void close();
        int getHour();
        int getMinute();
        void showDescriptionDialog();
        void setDescription(String description);
        Boolean getVibrationInfo();
        String getDescription();
        List<String> getDaysList();
        void showDaysDialog(List<String>daysList, List<Integer>checkedDays);
        void showRingtones();
        String getRingtone();
        long getTimeInMillis(int hour, int minute);
        void setActivityResult(int id);
        String getDaysOfWeek();
        void setDaysOfWeekText(String daysOfWeekText);
    }

    interface presenter{
        void onCloseWasClicked();
        void onDoneWasClicked();
        void onDescriptionWasClicked();
        void onDescriptionDone(String description);
        void onDaysWasClicked();
        void saveDaysWasClicked(int monday, int tuesday, int wednesday, int thursday, int friday,
                                int saturday, int sunday);
        void onRingtonesWasClicked();
    }

}
