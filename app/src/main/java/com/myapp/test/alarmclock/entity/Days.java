package com.myapp.test.alarmclock.entity;

import java.util.List;

public class Days {

    public Days(List <String> days) {
        this.days = days;
    }

    private List days;

    public List<String> getDays() {
        return days;
    }

    public void setDays(List <String> days) {
        this.days = days;
    }
}
