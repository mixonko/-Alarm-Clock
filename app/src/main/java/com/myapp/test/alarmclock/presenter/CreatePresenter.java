package com.myapp.test.alarmclock.presenter;

import com.myapp.test.alarmclock.contract.CreateContract;
import com.myapp.test.alarmclock.contract.RepositoryContract;
import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.model.Repository;

import java.util.List;

public class CreatePresenter implements CreateContract.presenter {

    private CreateContract.view view;
    private RepositoryContract repository;
    public CreatePresenter(CreateContract.view view) {
        this.view = view;
        repository = new Repository();
    }

    @Override
    public void onDoneWasClicked() {
        AlarmClock alarmClock = new AlarmClock(getHour(view.getHour()),
                getMinute(view.getMinute()), 0, true,
                view.getVibrationInfo(), view.getDescription(), view.getDaysOfWeek(),
                view.getRingtoneName(), view.getRingtonePath(),view.getPickedDaysText());
        repository.addAlarmClock(alarmClock);
        try {
            List<AlarmClock> list = repository.getAllAlarmClocks();
            alarmClock = list.get(list.size() - 1);
            view.setActivityResult(alarmClock.getId());
            view.close();
        } catch (Exception e) {  }

    }

    @Override
    public void onDescriptionWasClicked() {
        view.showDescriptionDialog();
    }

    @Override
    public void onDescriptionDone(String description) {
        view.setDescriptionText(description);
    }

    @Override
    public void onDaysWasClicked() {
        view.showDaysDialog(view.getDaysList(), view.getDaysOfWeek());
    }

    @Override
    public void saveDaysWasClicked() {
        view.setPickedDaysText(view.getPickedDaysText());
    }

    @Override
    public void onRingtonesWasClicked() {
        view.showRingtones();
    }

    @Override
    public void onRingtoneResult(String ringtoneName) {
        view.setRingtoneNameText(ringtoneName);
    }

    @Override
    public void onVibrationWasClicked() {
        view.setVibration(!view.getVibrationInfo());
    }

    private String getHour(int hour){
        String mHour = String.valueOf(hour);
        if (mHour.length() == 1){
            return "0" + mHour;
        }else {
            return mHour;
        }
    }

    private String getMinute(int minute){
        String mMinute = String.valueOf(minute);
        if (mMinute.length() == 1){
            return "0" + mMinute;
        }else {
            return mMinute;
        }
    }

}
