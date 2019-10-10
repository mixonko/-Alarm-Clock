package com.myapp.test.alarmclock.presenter;

import com.myapp.test.alarmclock.contract.MainContract;
import com.myapp.test.alarmclock.contract.RepositoryContract;
import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.model.Repository;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.myapp.test.alarmclock.model.Repository.database;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private RepositoryContract repository;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        repository = new Repository();
    }

    @Override
    public void onActivityCreate() {
        view.setList(repository.getAllAlarmClocks());
    }

    @Override
    public void onActivityResume() {
        setInfoText();
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
            view.alarmClockOn(Integer.parseInt(alarmClock.getHour()),
                    Integer.parseInt(alarmClock.getMinute()), alarmClock.getId());
            updateAlarmClock(alarmClock,true);
            view.showAlarmClockOn(alarmClock.getHour(), alarmClock.getMinute());
        }else {
            view.alarmClockOff(alarmClock.getId());
            updateAlarmClock(alarmClock,false);
            view.showAlarmClockOff(alarmClock.getHour(), alarmClock.getMinute());
        }
        setInfoText();
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
    public void onActivityResult(int id) {
        AlarmClock alarmClock = database.alarmClockDao().getAlarmClock(id);
        view.alarmClockOn(Integer.parseInt(alarmClock.getHour()),
                Integer.parseInt(alarmClock.getMinute()), alarmClock.getId());
        view.showAlarmClockOn(alarmClock.getHour(), alarmClock.getMinute());
        view.updateList(repository.getAllAlarmClocks());
    }

    @Override
    public void cancelWasReceived() {
        view.updateList(repository.getAllAlarmClocks());
        setInfoText();
    }

    private void updateAlarmClock(AlarmClock alarmClock, Boolean b){
        alarmClock.setAlarmClockOn(b);
        repository.updateAlarmClock(alarmClock);
    }

    private String getDifferenceTime() throws Exception {
        Calendar calendar = Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis();
        long myTimeInMillis = repository.getSortByTimemillis(true).get(0);
        long difference = myTimeInMillis - timeInMillis - 10800000;
        calendar.setTimeInMillis(difference);
        Date date = new Date(difference);
        DateFormat df = new SimpleDateFormat("Сработает через HH ч mm мин.");
        String time = df.format(date);
        return time;
    }

    private void setInfoText() {
        try {
            view.setInfoText(getDifferenceTime());
        } catch (Exception e) {
            view.clearInfoText();
        }
    }

}
