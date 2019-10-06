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
    public void onCreate(int id) {
        alarmClock = repository.getAlarmClock(id);
        view.setHour(Integer.parseInt(alarmClock.getHour()));
        view.setMinute(Integer.parseInt(alarmClock.getMinute()));
        view.setVibration(alarmClock.getVibration());
        view.setDescription(alarmClock.getDescription());
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
        repository.updateAlarmClock(alarmClock);
        view.createAlarmClock(Integer.parseInt(alarmClock.getHour()),
                Integer.parseInt(alarmClock.getMinute()), alarmClock.getId());
        view.showAlarmClockOn(alarmClock.getHour(), alarmClock.getMinute());
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
}
