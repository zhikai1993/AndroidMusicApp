package com.android.flashbackmusic;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by nataliepopescu on 2/17/18.
 */

public interface LocationInterface {

    double getLatitude();
    void setLatitude(double latitude);
    double getLongitude();
    void setLongitude(double longitude);
    LatLng getCurrentLocation();
    void establishLocationPermission(Context context, Activity activity);
    void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults);
  
}
