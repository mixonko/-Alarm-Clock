package com.myapp.test.alarmclock.presenter;

import com.myapp.test.alarmclock.contract.CreateContract;
import com.myapp.test.alarmclock.contract.RepositoryContract;
import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.model.Repository;

import java.util.ArrayList;
import java.util.List;

public class CreatePresenter implements CreateContract.presenter {

    private CreateContract.view view;
    private RepositoryContract repository;
    private int monday = 0, tuesday = 0, wednesday = 0, thursday = 0, friday = 0, saturday = 0, sunday = 0;

    public CreatePresenter(CreateContract.view view) {
        this.view = view;
        repository = new Repository();
    }

    @Override
    public void onCloseWasClicked() {
        view.close();
    }

    @Override
    public void onDoneWasClicked() {
        AlarmClock alarmClock = new AlarmClock(String.valueOf(view.getHour()),
                String.valueOf(view.getMinute()), true,
                view.getVibrationInfo(), view.getDescription(),
                monday, tuesday, wednesday, thursday, friday, saturday, sunday,
                view.getRingtone());
        repository.addAlarmClock(alarmClock);
        List<AlarmClock> list = repository.getAllAlarmClocks();
        alarmClock = list.get(list.size() - 1);
        view.createAlarmClock(Integer.parseInt(alarmClock.getHour()),
                Integer.parseInt(alarmClock.getMinute()), alarmClock.getId());
        view.showAlarmClockOn(alarmClock.getHour(), alarmClock.getMinute());
        view.setResult();
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
    }

    @Override
    public void onRingtonesWasClicked() {
        view.showRingtones();
    }

}
