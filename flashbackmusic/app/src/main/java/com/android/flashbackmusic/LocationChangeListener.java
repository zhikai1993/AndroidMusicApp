package com.android.flashbackmusic;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by vrkumar on 3/16/18.
 */

public interface LocationChangeListener {
    void onLocationChange(LatLng location);
}
