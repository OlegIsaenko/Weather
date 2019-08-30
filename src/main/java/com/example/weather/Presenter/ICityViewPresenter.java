package com.example.weather.Presenter;

import android.app.Activity;
import android.location.Location;

public interface ICityViewPresenter {
    void getCity(Integer cityId);

    void getCity(String cityName);

    void getCity(Location location);

    void getCurrentCity(Activity activity);

    void updateLocation(Activity activity);

    void getCityList(String cityName);

    void getCityFromDb(Integer cityId);

    void updateCity();

}
