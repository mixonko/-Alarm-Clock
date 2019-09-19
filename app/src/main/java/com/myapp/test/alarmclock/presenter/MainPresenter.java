package com.myapp.test.alarmclock.presenter;

import com.myapp.test.alarmclock.contracts.MainContract;
import com.myapp.test.alarmclock.contracts.RepositoryContract;
import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.model.Repository;


public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private RepositoryContract repository;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        repository = new Repository();
    }

    @Override
    public void onCreateActivity() {
        view.setAdapter(repository.getAllAlarmClock());
    }

    @Override
    public void onItemWasClicked(int position) {

    }

    @Override
    public void onCreateButtonWasClicked() {
        view.startCreateActivity();

    }

    @Override
    public void onSwitchWasChanged(int position, Boolean b, AlarmClock alarmClock) {
        if (b){
            updateAlarmClock(alarmClock,true);
            view.alarmClockOn(Integer.parseInt(alarmClock.getHour()), Integer.parseInt(alarmClock.getMinute()), alarmClock.getId());
        }else {
            updateAlarmClock(alarmClock,false);
            view.alarmClockOff(alarmClock.getId());

        }
    }

    @Override
    public void onResume() {
        view.setAdapter(repository.getAllAlarmClock());
    }

    private void updateAlarmClock(AlarmClock alarmClock, Boolean b){
        alarmClock.setAlarmClockOn(b);
        repository.updateAlarmClock(alarmClock);
    }

}
