package com.android.flashbackmusic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by nataliepopescu on 2/17/18.
 */

public class LocationMock implements LocationInterface {

    // The entry point to the Fused Location Provider.

    private String locationProvider;
    private LocationManager locationManager;
    private List<LocationChangeListener> locationChangeListenerList = new ArrayList<LocationChangeListener>();

    // Compose Location
    private Location location;
    private Context context;
    private Activity activity;
  
    //private double latDefault = 37;
    //private double lngDefault = 151;

    LocationMock() { //FusedLocationProviderClient client) {
        locationProvider = LocationManager.GPS_PROVIDER;
        location = new Location(locationProvider);
        //location.setLatitude(latDefault);
        //location.setLongitude(lngDefault);
    }

    public double getLatitude() { return location.getLatitude(); }

    public void setLatitude(double latitude) { location.setLatitude(latitude); }

    public double getLongitude() { return location.getLongitude(); }

    public void setLongitude(double longitude) { location.setLongitude(longitude); }

    public LatLng getCurrentLocation() {

        if (context == null) return null;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        Location tempLoc = locationManager.getLastKnownLocation(locationProvider);
        if (tempLoc != null) {
            location = tempLoc;
        }

        Log.v("CURLOC: ", "" + location);
        return new LatLng(location.getLatitude(), location.getLongitude());

    }

    public void establishLocationPermission(Context context, Activity activity) {
        Log.v("Establishing Location Permission", "start");
        if (context == null || activity == null) return;
        this.context = context;
        this.activity = activity;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
        } else {
            startLocationUpdates();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();

                } else {
                    Toast.makeText(context, "Flashback Mode Disabled", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void startLocationUpdates() {
        Log.v("Loc updates started","from onRequestPermissionsResult");
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.v("LOC", "Permission not granted.");
            return;
        }

        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location curLocation) {
                location = curLocation;
                notifyListeners();
                //Log.v("Location Changed!", "to " + curLocation.toString());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    public void addLocationChangeListener(LocationChangeListener s){
        this.locationChangeListenerList.add(s);
    }

    public void notifyListeners(){
        for (LocationChangeListener l : locationChangeListenerList){
            l.onLocationChange(new LatLng(this.location.getLatitude(), this.location.getLongitude()));
        }
    }

}
