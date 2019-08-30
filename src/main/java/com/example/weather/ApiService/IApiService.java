package com.example.weather.ApiService;

import com.example.weather.Model.City;

import java.util.List;

public interface IApiService {
    City getCity(String cityName);

    City getCity(Integer cityId);

    City getCity(Double lat, Double lon);

    List<City> getCityList(String cityName);
}
