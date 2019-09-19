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
        view.createAlarmClock(view.getHour(), view.getMinute(), alarmClock.getId());
        view.close();
    }

    @Override
    public void onDescriptionWasClicked() {
        view.setDescription();
    }

}
