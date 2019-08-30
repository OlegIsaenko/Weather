package com.example.weather.View;

import com.example.weather.Model.City;

public interface ICityViewFragment {
    void showCity(City city);

    void onError(String text);
}
