package com.android.flashbackmusic;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by raindrop on 3/14/18.
 */

public class Time {

    private boolean mocking = false;
    private Date mockableDate;
    private Calendar calendar;
    private TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");

    public Time() {
        // not mocking
        // use current time
        mocking = false;
        calendar = Calendar.getInstance(tz);
        setMockableDate(calendar.getTime());
    }

    public Time(boolean mocking, Date time) {
        setMockableDate(time);
        this.mocking = mocking;
        this.calendar = Calendar.getInstance(tz);
        this.calendar.setTime(time);
    }

    public Date getDate() {
        return mockableDate;
    }

    public Calendar getCalendar() {
        return this.calendar;
    }

    public void setMockableDate(Date time) {
        this.mockableDate = time;
    }

    public boolean isMocking() {
        return mocking;
    }

}
