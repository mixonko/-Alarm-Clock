package com.myapp.test.alarmclock.presenter;

import com.myapp.test.alarmclock.contracts.CreateContract;

public class CreatePresenter implements CreateContract.presenter {

    private CreateContract.view view;

    public CreatePresenter(CreateContract.view view) {
        this.view = view;
    }

    @Override
    public void onCancelWasClicked() {
        view.cancel();
    }

    @Override
    public void onDoneWasClicked() {
        view.done();
    }

    @Override
    public void onItemWasClicked() {

    }
}
