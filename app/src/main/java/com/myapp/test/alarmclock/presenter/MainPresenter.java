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
    public void onItemWasClicked(AlarmClock alarmClock) {
        view.startChangeActivity(alarmClock.getId());
    }

    @Override
    public void onItemWasLongClicked(AlarmClock alarmClock, int position) {
        view.showDeleteDialog(alarmClock, position);
    }

    @Override
    public void onSwitchWasChanged(Boolean b, AlarmClock alarmClock) {
        if (b){
            updateAlarmClock(alarmClock,true);
            view.alarmClockOn(Integer.parseInt(alarmClock.getHour()),
                    Integer.parseInt(alarmClock.getMinute()), alarmClock.getId());
            view.showAlarmClockOn(alarmClock.getHour(), alarmClock.getMinute());
        }else {
            updateAlarmClock(alarmClock,false);
            view.alarmClockOff(alarmClock.getId());
            view.showAlarmClockOff(alarmClock.getHour(), alarmClock.getMinute());
            view.deleteNotification(alarmClock.getId());
            view.stopVibration();
            view.stopRingtone();
        }
    }

    @Override
    public void onCreateButtonWasClicked() {
        view.startCreateActivity();

    }

    @Override
    public void onDeleteWasClicked(AlarmClock alarmClock, int position) {
        view.alarmClockOff(alarmClock.getId());
        repository.deleteAlarmClock(alarmClock);
        view.deleteItem(position);
    }

    @Override
    public void onResume() {
        view.setAdapter(repository.getAllAlarmClock());
    }

    @Override
    public void onReceive(int id) {
        AlarmClock alarmClock = repository.getAlarmClock(id);
        view.createNotification(alarmClock.getId(), alarmClock);
        view.playRingtone(alarmClock.getRingtone());
        if (alarmClock.getVibration())
            view.startVibration(25000);
    }

    @Override
    public void onReceiveOff(int id) {
        AlarmClock alarmClock = repository.getAlarmClock(id);
        alarmClock.setAlarmClockOn(false);
        repository.updateAlarmClock(alarmClock);
        view.alarmClockOff(alarmClock.getId());
        view.deleteNotification(alarmClock.getId());
        view.stopVibration();
        view.stopRingtone();
        view.setAdapter(repository.getAllAlarmClock());
        view.showAlarmClockOff(alarmClock.getHour(), alarmClock.getMinute());
    }

    private void updateAlarmClock(AlarmClock alarmClock, Boolean b){
        alarmClock.setAlarmClockOn(b);
        repository.updateAlarmClock(alarmClock);
    }

}
