package com.myapp.test.alarmclock.presenter;

import com.myapp.test.alarmclock.contracts.CreateContract;
import com.myapp.test.alarmclock.contracts.RepositoryContract;
import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.model.Repository;

public class CreatePresenter implements CreateContract.presenter {

    private CreateContract.view view;
    private RepositoryContract repository;

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
                view.getVibrationInfo(), view.getDescription());
        repository.addAlarmClock(alarmClock);
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

    @Override
    public void onDaysWasClicked() {
        view.showDaysDialog(view.getDaysList());
    }

}
