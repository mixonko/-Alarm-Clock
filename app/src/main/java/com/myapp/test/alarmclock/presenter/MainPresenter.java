package com.myapp.test.alarmclock.presenter;

import com.myapp.test.alarmclock.contracts.MainContract;
import com.myapp.test.alarmclock.contracts.RepositoryContract;
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
    public void onSwitchWasChanged(int position) {

    }

}
