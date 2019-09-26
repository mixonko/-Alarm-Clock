package com.myapp.test.alarmclock.presenter;

import android.widget.Toast;

import com.myapp.test.alarmclock.contracts.CreateContract;
import com.myapp.test.alarmclock.contracts.RepositoryContract;
import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.model.Repository;
import com.myapp.test.alarmclock.myAppContext.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class CreatePresenter implements CreateContract.presenter {

    private CreateContract.view view;
    private RepositoryContract repository;
    private int mMonday = 0, mTuesday = 0, mWednesday = 0, mThursday = 0, mFriday = 0, mSaturday = 0, mSunday = 0;
    private int monday = 0, tuesday = 0, wednesday = 0, thursday = 0, friday = 0, saturday = 0, sunday = 0;

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
                view.getVibrationInfo(), view.getDescription(), monday, tuesday, wednesday, thursday, friday, saturday, sunday);
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

    @Override
    public void daysWasChecked(int monday, int tuesday, int wednesday, int thursday, int friday, int saturday, int sunday) {
         mMonday = monday;
         mTuesday = tuesday;
         mWednesday = wednesday;
         mThursday = thursday;
         mFriday = friday;
         mSaturday = saturday;
         mSunday = sunday;
    }

    @Override
    public void saveDaysWasClicked() {
        monday = mMonday;
        tuesday = mTuesday;
        wednesday = mWednesday;
        thursday = mThursday;
        friday = mFriday;
        saturday = mSaturday;
        sunday = mSunday;
    }

}
