package com.android.flashbackmusic;

import android.location.LocationManager;
import android.support.test.rule.ActivityTestRule;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by nataliepopescu on 2/18/18.
 */

public class TestLocation {
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testSetGetLatitudeLongitude() {
        LocationMock locationMock = new LocationMock();

        locationMock.setLatitude(34);
        locationMock.setLongitude(178);

        LatLng loc = new LatLng(34, 178);

        assertEquals(loc.latitude, locationMock.getLatitude(), 0);
        assertEquals(loc.longitude, locationMock.getLongitude(), 0);

    }

    @Test
    public void testGetCurrentLocation() {
        LocationMock locationMock = new LocationMock();

        LatLng loc = locationMock.getCurrentLocation();

        // Without GPX
        assertEquals(null, loc);
    }
}
