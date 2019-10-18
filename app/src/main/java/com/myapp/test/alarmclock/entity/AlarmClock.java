package com.myapp.test.alarmclock.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity( tableName = "alarm_clock" )
public class AlarmClock {

    public AlarmClock(String hour, String minute, long timeInMillis, Boolean alarmClockOn,
                      Boolean vibration, String description, DaysOfWeek daysOfWeek,
                      String ringtoneName, String ringtonePath, String pickedDaysText) {
        this.hour = hour;
        this.minute = minute;
        this.timeInMillis = timeInMillis;
        this.alarmClockOn = alarmClockOn;
        this.vibration = vibration;
        this.description = description;
        this.daysOfWeek = daysOfWeek;
        this.ringtoneName = ringtoneName;
        this.ringtonePath = ringtonePath;
        this.pickedDaysText = pickedDaysText;
    }

    @PrimaryKey( autoGenerate = true )
    private int id;
    @ColumnInfo( name = "hour" )
    private String hour;
    @ColumnInfo( name = "minute" )
    private String minute;
    @ColumnInfo( name = "timeInMillis" )
    private long timeInMillis;
    @ColumnInfo( name = "alarm_clock_on" )
    private Boolean alarmClockOn;
    @ColumnInfo( name = "vibration" )
    private Boolean vibration;
    @ColumnInfo( name = "description" )
    private String description;
    @Embedded()
    private DaysOfWeek daysOfWeek;
    @ColumnInfo( name = "ringtoneName" )
    private String ringtoneName;
    @ColumnInfo( name = "ringtonePath" )
    private String ringtonePath;
    @ColumnInfo( name = "pickedDaysText" )
    private String pickedDaysText;

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

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
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

    public void setVibration(Boolean vibration) {
        this.vibration = vibration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DaysOfWeek getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(DaysOfWeek daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public String getRingtoneName() {
        return ringtoneName;
    }

    public void setRingtoneName(String ringtoneName) {
        this.ringtoneName = ringtoneName;
    }

    public String getRingtonePath() {
        return ringtonePath;
    }

    public void setRingtonePath(String ringtonePath) {
        this.ringtonePath = ringtonePath;
    }

    public String getPickedDaysText() {
        return pickedDaysText;
    }

    public void setPickedDaysText(String pickedDaysText) {
        this.pickedDaysText = pickedDaysText;
    }
}
