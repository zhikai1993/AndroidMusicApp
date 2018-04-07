package com.android.flashbackmusic;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by nataliepopescu on 2/17/18.
 */

public class CurrentParameters {
    private LocationInterface locationHandler;
    private Calendar calendar;
    private LatLng location;
    private String dayOfWeek;
    private String timeOfDay;
    private Time lastPlayedTime;
    private TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");

    private double latDefault = 37;
    private double lngDefault = 151;

    public CurrentParameters() {}

    public LatLng getLocation() {
        if (locationHandler == null) {
            Log.v("LOCATION HANDLER", "is null");
            return new LatLng(latDefault, lngDefault);
        }
        else {
            Log.v("LOCATION HANDLER", "is not null");
            return locationHandler.getCurrentLocation();
        }
    }

    public CurrentParameters(LocationInterface loc) {
        // Location
        locationHandler = loc;
        location = getLocation();
        //Log.d("cur location", "" + location.toString());

        Time time = new Time();
        setLastPlayedTime(time);

        dayOfWeek = getDayOfWeek();

        timeOfDay = getTimeOfDay();
      
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setLocation(LocationInterface loc) { location = loc.getCurrentLocation(); }

    public Time getLastPlayedTime() { return lastPlayedTime; }

    public void setLastPlayedTime(Time time) {
        lastPlayedTime = time;
        calendar = lastPlayedTime.getCalendar();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case 2: dayOfWeek = "Mon"; break;
            case 3: dayOfWeek = "Tues"; break;
            case 4: dayOfWeek = "Wed"; break;
            case 5: dayOfWeek = "Thu"; break;
            case 6: dayOfWeek = "Fri"; break;
            case 7: dayOfWeek = "Sat"; break;
            case 1: dayOfWeek = "Sun"; break;
            default: dayOfWeek = "BARN";
        }
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 5 && hour < 11) timeOfDay = "morning";
        else if (hour >= 11 && hour < 17) timeOfDay = "afternoon";
        else timeOfDay = "night";
        //Log.v("day of week", dayOfWeek);
        //Log.v("hour of day", timeOfDay);
    }

    //For testing purposes
    protected void setLatLng(LatLng latLng){location = latLng;}

    public void setDayOfWeek(String dow) { dayOfWeek = dow; }

    public void setTimeOfDay(String tod) { timeOfDay = tod; }
}
