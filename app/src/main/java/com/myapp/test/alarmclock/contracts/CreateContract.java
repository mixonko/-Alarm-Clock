package com.myapp.test.alarmclock.contracts;

import java.util.List;

public interface CreateContract {
    interface view {
        void close();
        int getHour();
        int getMinute();
        void showDescriptionDialog();
        void setDescription(String description);
        void createAlarmClock(int hour, int minute, int id);
        Boolean getVibrationInfo();
        String getDescription();
        void showAlarmClockOn(String hour, String minute);
        List<String> getDaysList();
        void showDaysDialog(List<String>daysList, List<Integer>checkedDays);
    }

    interface presenter{
        void onCloseWasClicked();
        void onDoneWasClicked();
        void onDescriptionWasClicked();
        void onDescriptionDone(String description);
        void onDaysWasClicked();
        void saveDaysWasClicked(int monday, int tuesday, int wednesday, int thursday, int friday, int saturday, int sunday);
    }

}
