package com.example.weather.LocationService;

import android.app.Activity;
import android.location.Address;
import android.location.Location;

import java.util.List;

import io.reactivex.Observable;

public interface IlocationService {

    Observable<Location> getLastLocation();

    Observable<Location> updateLocation();

    List<Address> getAddressFromLocation(Location location);
}
