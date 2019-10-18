package com.myapp.test.alarmclock.entity;

import java.util.ArrayList;
import java.util.List;

import androidx.room.ColumnInfo;

public class DaysOfWeek {

    public DaysOfWeek(int monday, int tuesday, int wednesday, int thursday, int friday, int saturday, int sunday) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    @ColumnInfo( name = "monday" )
    private int monday;
    @ColumnInfo( name = "tuesday" )
    private int tuesday;
    @ColumnInfo( name = "wednesday" )
    private int wednesday;
    @ColumnInfo( name = "thursday" )
    private int thursday;
    @ColumnInfo( name = "friday" )
    private int friday;
    @ColumnInfo( name = "saturday" )
    private int saturday;
    @ColumnInfo( name = "sunday" )
    private int sunday;

    public int getMonday() {
        return monday;
    }

    public void setMonday(int monday) {
        this.monday = monday;
    }

    public int getTuesday() {
        return tuesday;
    }

    public void setTuesday(int tuesday) {
        this.tuesday = tuesday;
    }

    public int getWednesday() {
        return wednesday;
    }

    public void setWednesday(int wednesday) {
        this.wednesday = wednesday;
    }

    public int getThursday() {
        return thursday;
    }

    public void setThursday(int thursday) {
        this.thursday = thursday;
    }

    public int getFriday() {
        return friday;
    }

    public void setFriday(int friday) {
        this.friday = friday;
    }

    public int getSaturday() {
        return saturday;
    }

    public void setSaturday(int saturday) {
        this.saturday = saturday;
    }

    public int getSunday() {
        return sunday;
    }

    public void setSunday(int sunday) {
        this.sunday = sunday;
    }

    public List<Integer> getDays() {
        List<Integer> days = new ArrayList();
        days.add(monday);
        days.add(tuesday);
        days.add(wednesday);
        days.add(thursday);
        days.add(friday);
        days.add(saturday);
        days.add(sunday);
        return days;
    }
}
