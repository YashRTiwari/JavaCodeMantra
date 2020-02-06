package tech.yashtiwari.verkada.retrofit.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static tech.yashtiwari.verkada.Utils.Constant.SDF;

public class DateAndDuration {

    private int hour;
    private int minute;
    private int day;
    private int year;
    private String month;
    private int duration;


    /**
     *
     * @param time - time in milliseconds
     * @param duration - time in seconds
     */
    public DateAndDuration(long time, int duration){
        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(time);
        Date date = calender.getTime();

        SimpleDateFormat d = new SimpleDateFormat("dd");
        SimpleDateFormat m = new SimpleDateFormat("MMM");
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        SimpleDateFormat h = new SimpleDateFormat("hh");
        SimpleDateFormat mm = new SimpleDateFormat("mm");

        Date parsedDate = null;
        try {
            parsedDate = SDF.parse(date.toString());

            setDay(Integer.parseInt(d.format(parsedDate)));
            setMonth(m.format(parsedDate));
            setYear(Integer.parseInt(y.format(parsedDate)));
            setHour(Integer.parseInt(h.format(parsedDate)));
            setMinute(Integer.parseInt(mm.format(parsedDate)));
            setoDuration(duration);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    public int getoDuration() {
        return oDuration;
    }

    /**
     * This function helps in creating the signal image,
     * by creating a percentile value.
     * @param maxDuration
     */
    public void updateDurationToPercent(int maxDuration){
        setDuration((int) (((float) getoDuration() / (float) maxDuration) * 100));
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
