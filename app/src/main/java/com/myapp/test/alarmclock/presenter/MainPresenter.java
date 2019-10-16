package com.myapp.test.alarmclock.presenter;

import android.widget.LinearLayout;

import com.myapp.test.alarmclock.contract.MainContract;
import com.myapp.test.alarmclock.contract.RepositoryContract;
import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.model.Repository;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
        view.showDeleteDialog(alarmClock, position, alarmClock.getPickedDays());
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
        view.setInfoText(getDifferenceTime());
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

    private String getDifferenceTime()  {
        Calendar calendar = Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis();
        long myTimeInMillis;
        String time;
        int minDays = 8;
        int id = 0;
        AlarmClock alarmClock;
        try {
            List<AlarmClock> a = repository.getSortByTimemillis(true);
            for (int i = 0; i < a.size(); i++) {
                Calendar c = Calendar.getInstance();
                int difference = 0;
                alarmClock = a.get(i);
                if (alarmClock.getMonday() == 0 && alarmClock.getTuesday() == 0
                        && alarmClock.getWednesday() == 0 && alarmClock.getThursday() == 0
                        && alarmClock.getFriday() == 0 && alarmClock.getSaturday() == 0
                        && alarmClock.getSunday() == 0) {
                    minDays = difference;
                    id = alarmClock.getId();
                    c.setTimeInMillis(alarmClock.getTimeInMillis());
                    if (c.before(Calendar.getInstance()))
                        minDays=+1;
                    break;
                }
                List<Integer> l = new ArrayList();
                l.add(alarmClock.getMonday());
                l.add(alarmClock.getTuesday());
                l.add(alarmClock.getWednesday());
                l.add(alarmClock.getThursday());
                l.add(alarmClock.getFriday());
                l.add(alarmClock.getSaturday());
                l.add(alarmClock.getSunday());

                for (int j = 0; j < l.size(); j++) {
                    if (l.contains(c.get(Calendar.DAY_OF_WEEK))){
                        c.setTimeInMillis(alarmClock.getTimeInMillis());
                        if (difference == 0 && c.before(Calendar.getInstance())) {
                            difference = 7;
                        }
                        break;
                    }
                    c.add(Calendar.DATE, 1);
                    difference++;
                    calendar.clear();
                }
                if (difference < minDays)
                minDays = difference;
                id = alarmClock.getId();
                calendar.setTimeInMillis(alarmClock.getTimeInMillis());

            }
            alarmClock = repository.getAlarmClock(id);
            myTimeInMillis = alarmClock.getTimeInMillis();
            calendar.setTimeInMillis(myTimeInMillis);
            if (calendar.before(Calendar.getInstance())) {
            minDays = minDays - 1;
        }


            long difference = myTimeInMillis - timeInMillis - 10800000;
            calendar.setTimeInMillis(difference);
            Date date = new Date(difference);
            DateFormat df = new SimpleDateFormat("Сработает через " + minDays +" д HH ч mm мин.");
            time = df.format(date) ;
        } catch (Exception e) {
            time = "Без будильников";
        }

        return time;
    }

    private void setInfoText() {
        view.setInfoText(getDifferenceTime());
    }

}
