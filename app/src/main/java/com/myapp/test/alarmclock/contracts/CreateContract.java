package com.myapp.test.alarmclock.contracts;

public interface CreateContract {
    interface view {
        void cancel();
        void done();
    }

    interface presenter{
        void onCancelWasClicked();
        void onDoneWasClicked();
        void onItemWasClicked();
    }

}
