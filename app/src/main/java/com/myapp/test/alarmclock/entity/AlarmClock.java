package com.myapp.test.alarmclock.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity( tableName = "alarm_clock" )
public class AlarmClock {

    public AlarmClock(String hour, String minute, Boolean alarmClockOn, Boolean vibration, String description, Days days) {
        this.hour = hour;
        this.minute = minute;
        this.alarmClockOn = alarmClockOn;
        this.vibration = vibration;
        this.description = description;
        this.days = days;
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
    @ColumnInfo( name = "days" )
    private Days days;

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

    public Days getDays() {
        return days;
    }

    public void setDays(Days days) {
        this.days = days;
    }
}
