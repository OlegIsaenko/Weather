package com.example.weather.LocationService;

import android.app.Activity;
import android.location.Address;
import android.location.Location;

import java.util.List;

import io.reactivex.Observable;

public interface IlocationService {

    Observable<Location> getLastLocation(Activity activity);

    Observable<Location> updateLocation(Activity activity);

    List<Address> getAddressFromLocation(Activity activity, Location location);
}
