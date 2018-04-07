package com.android.flashbackmusic;

import com.google.android.gms.maps.model.LatLng;
import java.util.Date;

public class SongPlay {

    public String username;
    public Double latitude;
    public Double longitude;
    public String time;
    public Date date;

    public SongPlay(){}

    public SongPlay(String username, LatLng location, String time, Date date) {
        this.username = username;
        this.latitude = location.latitude;
        this.longitude = location.longitude;
        this.time = time;
        this.date = date;
    }
}
