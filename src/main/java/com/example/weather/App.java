package com.example.weather;

import android.app.Application;

import androidx.room.Room;

import com.example.weather.ApiService.WeatherServiceApi;
import com.example.weather.ApiService.WeatherService;
import com.example.weather.DbService.AppDatabase;
import com.example.weather.LocationService.LocationService;

public class App extends Application {

    private static App instance;
    private AppDatabase database;
    private LocationService locationService;
    private WeatherServiceApi weatherServiceApi;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(
                this,
                AppDatabase.class,
                "cities")
                .build();
        locationService = new LocationService(this);
        weatherServiceApi = WeatherService.getClient().create(WeatherServiceApi.class);
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public LocationService getLocationService() {
        return locationService;
    }

    public WeatherServiceApi getApiService() {
        return weatherServiceApi;
    }
}
