package com.android.flashbackmusic;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Float.parseFloat;

/**
 * Stores and retrieves user-specific song info in shared preferences
 */

public class SharedPrefsIO implements SongInfoIO {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SharedPrefsIO(SharedPreferences prefs) {
        this.prefs = prefs;
        setup();
    }

    public void setup() {
    }

    public void populateSongInfo(Song s) {
        String keyPrefix = s.getTitle() + s.getArtist();
        Log.v("print lat lng", s.getLastLocation().toString());

        Log.v("from_populateSongInfo", keyPrefix);

        if (prefs.contains(keyPrefix + "favorited")) {
            boolean favorited = prefs.getBoolean(keyPrefix + "favorited", false);
            s.setFavorited(favorited);
        }
        if (prefs.contains(keyPrefix + "disliked")) {
            boolean disliked = prefs.getBoolean(keyPrefix + "disliked", false);
            Log.v("disliked status", String.valueOf(disliked));
            s.setDisliked(disliked);
        }
        if (prefs.contains(keyPrefix + "locs")) {
            Set<String> fetch = prefs.getStringSet(keyPrefix + "locs", null);
            ArrayList<LatLng> locs = stringsToLatLng(fetch);
            s.setLocations(locs);
        }
        if (prefs.contains(keyPrefix + "lastplayedtime")) {
            long lastPlayedTime = prefs.getLong(keyPrefix + "lastplayedtime", 0);
            Date lastPlayedDate = new Date(lastPlayedTime);
            Time time = new Time(true, lastPlayedDate);
            s.setLastPlayedTime(time);
        }
        if (prefs.contains(keyPrefix + "timesofday")) {
            Set<String> timesOfDay = prefs.getStringSet(keyPrefix + "timesofday", null);
            s.setTimesOfDay(timesOfDay);
        }
        if (prefs.contains(keyPrefix + "daysofweek")) {
            Set<String> daysOfWeek = prefs.getStringSet(keyPrefix + "daysofweek", null);
            s.setDaysOfWeek(daysOfWeek);
        }
        if (prefs.contains(keyPrefix + "lastloc")) {
            String strLastLoc = prefs.getString(keyPrefix + "lastloc", "");
            LatLng lastloc = stringToLatLng(strLastLoc);
            s.setLastLocation(lastloc);
        }

    }

    public void storeSongInfo(Song s){
        // Get info from song
        boolean favorited = s.isFavorited();
        boolean disliked = s.isDisliked();
        ArrayList<LatLng> locations = s.getLocations();
        Set<String> locs = latLngsToString(locations);
        long lastPlayedTime = 0;
        if (s.getLastPlayedTime() != null) {
            lastPlayedTime = s.getLastPlayedTime().getDate().getTime();
        }
        Set<String> timesOfDay = s.getTimesOfDay();
        Set<String> daysOfWeek = s.getDaysOfWeek();
        String strLastLoc = s.getLastLocation().toString();
        Log.v("print lat lng", s.getLastLocation().toString());
        Log.v("print dislike status", String.valueOf(s.isDisliked()));
        editor = prefs.edit();

        String keyPrefix = s.getTitle() + s.getArtist();
        // Store into shared preferences
        Log.v("from_storeSongInfo", keyPrefix);
        editor.putBoolean(keyPrefix + "favorited", favorited);
        editor.putBoolean(keyPrefix + "disliked", disliked);
        editor.putStringSet(keyPrefix + "locs", locs);
        editor.putLong(keyPrefix + "lastplayedtime", lastPlayedTime);
        editor.putStringSet(keyPrefix + "timesofday", timesOfDay);
        editor.putStringSet(keyPrefix + "daysofweek", daysOfWeek);
        editor.putString(keyPrefix + "lastloc", strLastLoc);

        editor.apply();
    }

    private Set<String> latLngsToString(ArrayList<LatLng> locations) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < locations.size(); i++) {
            LatLng currLatLng = locations.get(i);
            String currString = currLatLng.toString();
            set.add(currString);
        }
        return set;
    }

    private ArrayList<LatLng> stringsToLatLng(Set<String> locations) {
        ArrayList<LatLng> locs = new ArrayList<>();
        int commaPos;
        float longitude, latitude;
        for (String curr : locations) {
            locs.add(stringToLatLng(curr));
        }
        return locs;
    }

    private LatLng stringToLatLng(String string) {
        int left = string.indexOf('(');
        int commaPos = string.indexOf(',');
        int right = string.indexOf(')');
        float longitude = parseFloat(string.substring(left + 1, commaPos));
        float latitude = parseFloat(string.substring(commaPos + 1, right));
        return new LatLng(longitude, latitude);
    }

    public void teardown() {
    }
}