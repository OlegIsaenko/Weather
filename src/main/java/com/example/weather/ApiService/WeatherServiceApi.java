package com.example.weather.ApiService;

import com.example.weather.Model.City.City;
import com.example.weather.Model.Forecast.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherServiceApi {
    @GET("weather")
    Call<City> getCityByLocation(
            @Query("lat") Double lat,
            @Query("lon") Double lon,
            @Query("appid") String appid,
            @Query("units") String units
    );

    @GET("forecast")
    Call<Forecast> getForecastByLocation(
            @Query("lat") Double lat,
            @Query("lon") Double lon,
            @Query("appid") String appid,
            @Query("units") String units
    );

    @GET("weather")
    Call<City> getCityByName(
            @Query("q") String cityName,
            @Query("appid") String apiKey,
            @Query("units") String units
    );

    @GET("forecast")
    Call<Forecast> getForecastByName(
            @Query("q") String cityName,
            @Query("appid") String apiKey,
            @Query("units") String units
    );
}
