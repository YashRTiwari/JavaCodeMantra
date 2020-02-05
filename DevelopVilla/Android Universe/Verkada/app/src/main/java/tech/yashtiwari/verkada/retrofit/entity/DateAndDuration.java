package tech.yashtiwari.verkada.retrofit.entity;

public class DateAndDuration {

    private int hour;
    private int minute;
    private int day;
    private int year;
    private String month;
    private int duration;

    public int getoDuration() {
        return oDuration;
    }

    public void setoDuration(int oDuration) {
        this.oDuration = oDuration;
    }

    private int oDuration;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
