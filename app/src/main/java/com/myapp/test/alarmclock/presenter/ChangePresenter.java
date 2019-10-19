package com.myapp.test.alarmclock.presenter;

import com.myapp.test.alarmclock.contract.ChangeContract;
import com.myapp.test.alarmclock.contract.RepositoryContract;
import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.model.Repository;

public class ChangePresenter implements ChangeContract.presenter {
    private ChangeContract.view view;
    private RepositoryContract repository;
    private AlarmClock alarmClock;

    public ChangePresenter(ChangeContract.view view) {
        this.view = view;
        repository = new Repository();
    }

    @Override
    public void onActivityCreate(int id) {
        alarmClock = repository.getAlarmClock(id);

        view.setHour(Integer.parseInt(alarmClock.getHour()));
        view.setMinute(Integer.parseInt(alarmClock.getMinute()));
        view.setVibration(alarmClock.getVibration());
        view.setDescription(alarmClock.getDescription());
        view.setRingtoneNameText(alarmClock.getRingtoneName());
        view.setPickedDaysText(alarmClock.getPickedDaysText());
        view.setDaysOfWeek(alarmClock.getDaysOfWeek());
    }
 
    @Override
    public void onDoneWasClicked() {
        alarmClock.setHour(getHour(view.getHour()));
        alarmClock.setMinute(getMinute(view.getMinute()));
        alarmClock.setAlarmClockOn(true);
        alarmClock.setVibration(view.getVibrationInfo());
        alarmClock.setDescription(view.getDescription());
        alarmClock.setDaysOfWeek(view.getDaysOfWeek());
        alarmClock.setRingtonePath(view.getRingtonePath());
        alarmClock.setRingtoneName(view.getRingtoneName());
        alarmClock.setPickedDaysText(view.getPickedDaysText());
        alarmClock.setTimeInMillis(view.getTimeInMillis(view.getHour(), view.getMinute()));
        repository.updateAlarmClock(alarmClock);
        view.setActivityResult(alarmClock.getId());
        view.close();
    }

    @Override
    public void onDescriptionWasClicked() {
        view.showDescriptionDialog();
    }

    @Override
    public void onDescriptionDone(String description) {
        view.setDescription(description);
    }

    @Override
    public void onDaysWasClicked() {
        view.showDaysDialog(view.getDaysList(), view.getDaysOfWeek());
    }

    @Override
    public void saveDaysWasClicked() {
        view.setPickedDaysText(view.getPickedDaysText());
    }

    @Override
    public void onRingtonesWasClicked() {
        view.showRingtones();
    }

    @Override
    public void onVibrationWasClicked() {
        view.setVibration(!view.getVibrationInfo());
    }

    @Override
    public void onRingtoneResult(String ringtoneName) {
        view.setRingtoneNameText(ringtoneName);
    }

    private String getHour(int hour){
        String mHour = String.valueOf(hour);
        if (mHour.length() == 1){
            return "0" + mHour;
        }else {
            return mHour;
        }
    }

    private String getMinute(int minute){
        String mMinute = String.valueOf(minute);
        if (mMinute.length() == 1){
            return "0" + mMinute;
        }else {
            return mMinute;
        }
    }
}
