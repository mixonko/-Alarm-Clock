package com.myapp.test.alarmclock.presenter;

import com.myapp.test.alarmclock.contract.MainContract;
import com.myapp.test.alarmclock.contract.RepositoryContract;
import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.model.Repository;

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
    public void onItemWasClicked(AlarmClock alarmClock) {
        view.startChangeActivity(alarmClock.getId());
    }

    @Override
    public void onItemWasLongClicked(AlarmClock alarmClock, int position) {
        view.showDeleteDialog(alarmClock, position, alarmClock.getPickedDaysText());
    }

    @Override
    public void onSwitchWasChanged(Boolean b, AlarmClock alarmClock) {
        if (b) {
            alarmClockOn(alarmClock);
        } else {
            view.alarmClockOff(alarmClock.getId());
            updateAlarmClock(alarmClock, false, 0L);
            setInfoText();
        }
    }

    @Override
    public void onActivityResult(int id) {
        AlarmClock alarmClock = database.alarmClockDao().getAlarmClock(id);
        alarmClockOn(alarmClock);
    }

    private void alarmClockOn(AlarmClock alarmClock) {
        Long timeInMillis = view.alarmClockOn(alarmClock.getId(), Integer.parseInt(alarmClock.getHour()),
                Integer.parseInt(alarmClock.getMinute()), alarmClock.getDaysOfWeek());
        updateAlarmClock(alarmClock, true, timeInMillis);
        setInfoText();
        view.showAlarmClockOn(differenceTime(alarmClock.getTimeInMillis()));
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
        setInfoText();
    }

    @Override
    public void updateWasReceived() {
        view.updateList(repository.getAllAlarmClocks());
        setInfoText();
    }

    @Override
    public void onActivityResume() {
        view.updateList(repository.getAllAlarmClocks());
        setInfoText();
    }

    private void setInfoText() {
        try {
            view.setInfoText(differenceTime(repository.getWorkingAlarms(true).get(0)));
        } catch (Exception e) {
            view.setInfoText("Нет будильников");
        }
    }

    private void updateAlarmClock(AlarmClock alarmClock, Boolean b, Long timeInMillis) {
        alarmClock.setAlarmClockOn(b);
        alarmClock.setTimeInMillis(timeInMillis);
        repository.updateAlarmClock(alarmClock);
    }

    public String differenceTime(Long myTimeInMillis) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Сработает через ");
        Long timeInMillis = Calendar.getInstance().getTimeInMillis();
        long difference = myTimeInMillis - timeInMillis;
        long days = (difference / (24 * 60 * 60 * 1000));
        long d = 24 * 60 * 60 * 1000 * days;
        long hour = (difference - d) / (60 * 60 * 1000);
        long h = 1000 * 60 * 60 * hour;
        long minute = (difference - d - h) / (60 * 1000);
        if (days != 0) {
            stringBuffer.append(days + " д. ");
        }
        if (hour != 0) {
            stringBuffer.append(hour + " ч. ");
        }

        if (minute != 0) {
            stringBuffer.append(minute + " мин.");
        }
        if (days == 0 && hour == 0 && minute == 0) {
            stringBuffer.append("1 минуту");
        }
        return stringBuffer.toString();
    }

}
