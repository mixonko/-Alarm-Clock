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
        repository.addAlarmClock(new AlarmClock(String.valueOf(view.getHour()),
                String.valueOf(view.getMinute())));
        view.startAlarmClock(view.getHour(), view.getMinute());
        view.close();

    }

    @Override
    public void onDaysOfTheWeekWasClicked() {

    }

    @Override
    public void onSoundWasClicked() {

    }

    @Override
    public void onVibrationSignalWasClicked() {

    }

    @Override
    public void onDescriptionWasClicked() {
        view.setDescription();
    }

}
