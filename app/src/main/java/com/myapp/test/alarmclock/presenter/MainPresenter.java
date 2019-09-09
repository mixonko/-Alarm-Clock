package com.myapp.test.alarmclock.presenter;

import com.myapp.test.alarmclock.contracts.MainContract;
import com.myapp.test.alarmclock.entity.AlarmClock;
import com.myapp.test.alarmclock.model.MainRepository;

import java.util.List;

public class MainPresenter implements MainContract.Presenter, MainContract.Repository.OnFinishedListener {
    private MainContract.View view;
    private MainContract.Repository repository;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        repository = new MainRepository();
    }

    @Override
    public void onItemWasClicked(int position) {

    }

    @Override
    public void onCreateButtonWasClicked() {

    }

    @Override
    public void onSwitchWasClicked() {

    }

    @Override
    public void onFinished(List<AlarmClock> list) {

    }
}
