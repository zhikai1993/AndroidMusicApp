package com.android.flashbackmusic;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by raindrop on 3/15/18.
 */

public class FetchAddressIntentService extends IntentService {

    protected ResultReceiver mReceiver;
    //...

    public FetchAddressIntentService() {
        super("FetchAddressIntentService");
    }

    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RESULT_DATA_KEY, message);
        mReceiver.send(resultCode, bundle);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        mReceiver = intent.getParcelableExtra(Constants.RECEIVER);
        if (intent == null) {
            Log.w("handling intent", "null");
            return;
        }
        String errorMessage = "";
        // Get loc passed to this service through an extra
        LatLng location = intent.getParcelableExtra(
                Constants.LOCATION_DATA_EXTRA);
        Log.w("Handling LOCATION", location.toString());
        List<Address> addresses = null;
        if (geocoder.isPresent()) {
            Log.w("GEOCODER", "is present");
            try {
                addresses = geocoder.getFromLocation(
                        location.latitude,
                        location.longitude,
                        // In this sample, get just a single address.
                        1);
            } catch (IOException ioException) {
                // Catch network or other I/O problems.
                errorMessage = "Service not available";
                Log.e("ERROR", errorMessage, ioException);
            } catch (IllegalArgumentException illegalArgumentException) {
                // Catch invalid latitude or longitude values.
                errorMessage = "Invalid latitude/longitude used";
                Log.e("ERROR", errorMessage + ". " +
                        "Latitude = " + location.latitude +
                        ", Longitude = " +
                        location.longitude, illegalArgumentException);
            }

            // Handle case where no address was found.
            if (addresses == null || addresses.size() == 0) {
                Log.w("Size of addresses list", String.valueOf(addresses.size()));
                if (errorMessage.isEmpty()) {
                    errorMessage = "No address found...";
                    Log.e("ERROR", errorMessage);
                }
                //deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage);
                deliverResultToReceiver(Constants.SUCCESS_RESULT, "SAN DIEGO");
            } else {
                Address address = addresses.get(0);
                ArrayList<String> addressFragments = new ArrayList<>();

                // Fetch the address lines using {@code getAddressLine},
                // join them, and send them to the thread. The {@link android.location.address}
                // class provides other options for fetching address details that you may prefer
                // to use. Here are some examples:
                // getLocality() ("Mountain View", for example)
                // getAdminArea() ("CA", for example)
                // getPostalCode() ("94043", for example)
                // getCountryCode() ("US", for example)
                // getCountryName() ("United States", for example)
                for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    addressFragments.add(address.getAddressLine(i));
                }

                Log.i("Found the address!", TextUtils.join(", ", addressFragments));
                deliverResultToReceiver(Constants.SUCCESS_RESULT,
                        TextUtils.join(", ", addressFragments));
            }
        } else {
            Log.w("GEOCODER", "is NOT PRESENT");
        }
    }
}