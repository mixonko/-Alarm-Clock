package com.myapp.test.alarmclock.presenter;

import com.myapp.test.alarmclock.contract.MainContract;
import com.myapp.test.alarmclock.contract.RepositoryContract;
import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.model.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    public void onItemWasClicked(AlarmClock alarmClock) {
        view.startChangeActivity(alarmClock.getId());
    }

    @Override
    public void onItemWasLongClicked(AlarmClock alarmClock, int position) {
        view.showDeleteDialog(alarmClock, position, alarmClock.getPickedDays());
    }

    @Override
    public void onSwitchWasChanged(Boolean b, AlarmClock alarmClock) {
        if (b){
            alarmClockOn(alarmClock);
        }else {
            view.alarmClockOff(alarmClock.getId());
            updateAlarmClock(alarmClock,false, 0L);
            setInfoText();
        }
    }

    @Override
    public void onActivityResult(int id) {
        AlarmClock alarmClock = database.alarmClockDao().getAlarmClock(id);
        alarmClockOn(alarmClock);
    }

    private void alarmClockOn(AlarmClock alarmClock){
        Long timeInMillis = view.alarmClockOn(alarmClock.getId(), Integer.parseInt(alarmClock.getHour()),
                Integer.parseInt(alarmClock.getMinute()), alarmClock.getMonday(),
                alarmClock.getTuesday(), alarmClock.getWednesday(), alarmClock.getThursday(),
                alarmClock.getFriday(), alarmClock.getSaturday(), alarmClock.getSunday());
        updateAlarmClock(alarmClock,true, timeInMillis);
        setInfoText();
        view.showAlarmClockOn(alarmClock.getHour(), alarmClock.getMinute());
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
    public void cancelWasReceived() {
        view.updateList(repository.getAllAlarmClocks());
    }

    @Override
    public void onActivityResume() {
        view.updateList(repository.getAllAlarmClocks());
        setInfoText();
    }

    private void setInfoText(){
        try {
            view.setInfoText(timeUntilNextAlarmClock());
        } catch (Exception e) {
            view.setInfoText("Нет будильников");
        }
    }

    private void updateAlarmClock(AlarmClock alarmClock, Boolean b, Long timeInMillis){
        alarmClock.setAlarmClockOn(b);
        alarmClock.setTimeInMillis(timeInMillis);
        repository.updateAlarmClock(alarmClock);
    }

    public String timeUntilNextAlarmClock() throws Exception {
        DateFormat df;
        Long myTimeInMillis = repository.getSortByTimemillis(true).get(0);
        Calendar calendar = Calendar.getInstance();
        Long timeInMillis = calendar.getTimeInMillis();
        long difference = myTimeInMillis - timeInMillis - 10800000 - 86400000 ;
        if (difference > 86340000){
            df = new SimpleDateFormat("Сработает через dd д HH ч mm мин.");
        }else{
            df = new SimpleDateFormat("Сработает через HH ч mm мин.");

        }

        Date date = new Date(difference);
        String time = df.format(date) ;
        return time;
    }

}
