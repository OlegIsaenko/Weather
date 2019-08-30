package com.example.weather.View;

import com.example.weather.Model.City;

import java.util.List;

public interface ICitySearchFragment {
    void showCityList(List<City> cityList);

    void onError(String message);
}
