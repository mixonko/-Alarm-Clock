package com.myapp.test.alarmclock.presenter;

import com.myapp.test.alarmclock.contracts.MainContract;
import com.myapp.test.alarmclock.model.MainRepository;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private MainContract.Repository repository;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        repository = new MainRepository();
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
    public void onSwitchWasClicked(int position) {

    }

}
