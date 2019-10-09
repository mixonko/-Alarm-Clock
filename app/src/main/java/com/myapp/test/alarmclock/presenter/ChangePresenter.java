package com.myapp.test.alarmclock.presenter;

import android.widget.Toast;

import com.myapp.test.alarmclock.contract.ChangeContract;
import com.myapp.test.alarmclock.contract.RepositoryContract;
import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.model.Repository;

import java.util.ArrayList;
import java.util.List;

public class ChangePresenter implements ChangeContract.presenter {
    private ChangeContract.view view;
    private RepositoryContract repository;
    private AlarmClock alarmClock;
    private int monday = 0, tuesday = 0, wednesday = 0, thursday = 0, friday = 0, saturday = 0, sunday = 0;

    public ChangePresenter(ChangeContract.view view) {
        this.view = view;
        repository = new Repository();
    }


    @Override
    public void onActivityCreate(int id) {
        alarmClock = repository.getAlarmClock(id);

        monday = alarmClock.getMonday();
        tuesday = alarmClock.getTuesday();
        wednesday = alarmClock.getWednesday();
        thursday = alarmClock.getThursday();
        friday = alarmClock.getFriday();
        saturday = alarmClock.getSaturday();
        sunday = alarmClock.getSunday();
        view.setHour(Integer.parseInt(alarmClock.getHour()));
        view.setMinute(Integer.parseInt(alarmClock.getMinute()));
        view.setVibration(alarmClock.getVibration());
        view.setDescription(alarmClock.getDescription());
        view.setRingtone(alarmClock.getRingtone());
        view.setDaysOfWeekText(alarmClock.getDays());
    }

    @Override
    public void onCloseWasClicked() {
        view.close();
    }

    @Override
    public void onDoneWasClicked() {
        alarmClock.setHour(String.valueOf(view.getHour()));
        alarmClock.setMinute(String.valueOf(view.getMinute()));
        alarmClock.setAlarmClockOn(true);
        alarmClock.setVibration(view.getVibrationInfo());
        alarmClock.setDescription(view.getDescription());
        alarmClock.setMonday(monday);
        alarmClock.setTuesday(tuesday);
        alarmClock.setWednesday(wednesday);
        alarmClock.setThursday(thursday);
        alarmClock.setFriday(friday);
        alarmClock.setSaturday(saturday);
        alarmClock.setSunday(sunday);
        alarmClock.setRingtone(view.getRingtone());
        alarmClock.setDays(view.getDaysOfWeek());
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
        List<Integer> checkedDays = new ArrayList<>();
        checkedDays.add(monday);
        checkedDays.add(tuesday);
        checkedDays.add(wednesday);
        checkedDays.add(thursday);
        checkedDays.add(friday);
        checkedDays.add(saturday);
        checkedDays.add(sunday);
        view.showDaysDialog(view.getDaysList(), checkedDays);
    }

    @Override
    public void saveDaysWasClicked(int monday, int tuesday, int wednesday, int thursday, int friday, int saturday, int sunday) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        view.setDaysOfWeekText(view.getDaysOfWeek());
    }

    @Override
    public void onRingtonesWasClicked() {
        view.showRingtones();
    }
}
