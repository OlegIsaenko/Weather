package com.example.weather.Presenter;

import com.example.weather.Model.City.City;
import com.example.weather.Model.Forecast.Forecast;

public interface ICityViewPresenter {

    void getCity(String cityName);

    void getForecast(String cityName);

    void getCity(Double lat, Double lon);

    void getForecast(Double lat, Double lon);

    void getCurrentLocation();

    void onResume();

    void onDestroy();

    void onFinished(City city);

    void onFinished(Forecast forecast);

}
