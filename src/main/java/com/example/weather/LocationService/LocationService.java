package com.example.weather.LocationService;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;

import static com.example.weather.View.CityViewActivity.TAG;


public class LocationService implements IlocationService{
    private Context context;

    public LocationService(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public Observable<Location> getLastLocation() {
        FusedLocationProviderClient client =
                LocationServices.getFusedLocationProviderClient(context);

        Task<Location> task = client.getLastLocation();

        return Observable.create(subscriber ->
                task.addOnSuccessListener(location -> subscriber.onNext(location))
        );
    }

    @Override
    public Observable<Location> updateLocation() {
        LocationRequest request = LocationRequest.create();
        request.setNumUpdates(1);
        request.setInterval(0);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder()
                        .addLocationRequest(request);

        SettingsClient settingsClient = LocationServices.getSettingsClient(context);
        Task<LocationSettingsResponse> task = settingsClient
                .checkLocationSettings(builder.build());
        task.addOnSuccessListener(locationSettingsResponse -> {
            Log.i(TAG, "getLastLocation: GPS USABLE: " +
                    locationSettingsResponse.getLocationSettingsStates().isGpsUsable() + "\n" +
                    locationSettingsResponse.getLocationSettingsStates().isNetworkLocationUsable() + "\n" +
                    locationSettingsResponse.getLocationSettingsStates().isBleUsable());
        });

        return Observable.create(emitter -> {
            LocationCallback callback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    emitter.onNext(locationResult.getLastLocation());
                }
            };

            FusedLocationProviderClient client =
                    LocationServices.getFusedLocationProviderClient(context);
            client.requestLocationUpdates(request, callback, null);
        });
    }

    @Override
    public List<Address> getAddressFromLocation(Location location) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> address = new ArrayList<>();
        try {
            address = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Address adr : address) {
            Log.i(TAG, "address: " + adr.getAddressLine(0));
        }
        return address;
    }


}
