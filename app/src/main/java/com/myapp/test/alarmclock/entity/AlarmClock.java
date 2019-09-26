package com.myapp.test.alarmclock.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity( tableName = "alarm_clock" )
public class AlarmClock {

    public AlarmClock(String hour, String minute, Boolean alarmClockOn, Boolean vibration, String description, int monday, int tuesday, int wednesday, int thursday, int friday, int saturday, int sunday) {
        this.hour = hour;
        this.minute = minute;
        this.alarmClockOn = alarmClockOn;
        this.vibration = vibration;
        this.description = description;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    @PrimaryKey( autoGenerate = true )
    private int id;
    @ColumnInfo( name = "hour" )
    private String hour;
    @ColumnInfo( name = "minute" )
    private String minute;
    @ColumnInfo( name = "alarm_clock_on" )
    private Boolean alarmClockOn;
    @ColumnInfo( name = "vibration" )
    private Boolean vibration;
    @ColumnInfo( name = "description" )
    private String description;
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public Boolean getAlarmClockOn() {
        return alarmClockOn;
    }

    public void setAlarmClockOn(Boolean alarmClockOn) {
        this.alarmClockOn = alarmClockOn;
    }

    public Boolean getVibration() {
        return vibration;
    }

    public void setVibration(Boolean mySwitch) {
        this.vibration = mySwitch;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
}
