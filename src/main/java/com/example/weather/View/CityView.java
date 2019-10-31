package com.example.weather.View;

import com.example.weather.Model.Forecast.ForecastItemAdapter;
import com.example.weather.Model.City.CityAdapter;

import java.util.List;

public interface CityView {
    void showProgress();

    void hideProgress();

    void setForecast(List<ForecastItemAdapter> forecast);

    void setCity(CityAdapter cityItem);
}
