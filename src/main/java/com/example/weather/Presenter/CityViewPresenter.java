package com.example.weather.Presenter;

import android.util.Log;

import com.example.weather.ApiService.Constants;
import com.example.weather.ApiService.WeatherServiceApi;
import com.example.weather.App;
import com.example.weather.DbService.AppDatabase;
import com.example.weather.Model.Forecast.ForecastItemAdapter;
import com.example.weather.LocationService.IlocationService;
import com.example.weather.Model.City.City;
import com.example.weather.Model.City.CityAdapter;
import com.example.weather.Model.Forecast.Forecast;
import com.example.weather.Model.Forecast.ForecastItems;
import com.example.weather.View.CityView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.weather.View.CityViewActivity.TAG;

public class CityViewPresenter implements ICityViewPresenter {

    private CityView cityView;
    private AppDatabase mDatabase;
    private IlocationService locationService;
    private WeatherServiceApi weatherServiceApi;

    public CityViewPresenter(CityView view) {
        cityView = view;
        mDatabase = App.getInstance().getDatabase();
        locationService = App.getInstance().getLocationService();
        weatherServiceApi = App.getInstance().getApiService();
    }

    @Override
    public void getCity(String cityName) {
        Call<City> cityCall = weatherServiceApi.getCityByName(cityName,
                Constants.WEATHER_API_KEY,
                Constants.DEFAULT_UNITS);
        cityCall.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                City city = response.body();
                if (response.isSuccessful()) {
                    onFinished(city);
                }
            }
            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void getForecast(String cityName) {
        Call<Forecast> cityCall = weatherServiceApi
                .getForecastByName(cityName, Constants.WEATHER_API_KEY, Constants.DEFAULT_UNITS);
        cityCall.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                Forecast forecast = response.body();
                if (response.isSuccessful()) {
                    if (forecast != null) {
                        onFinished(forecast);
                    }
                    else Log.i(TAG, "onResponse: NULL FORECAST");
                }
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void getCity(Double lat, Double lon) {
        Call<City> cityCall = weatherServiceApi
                .getCityByLocation(lat, lon, Constants.WEATHER_API_KEY, Constants.DEFAULT_UNITS);
        Log.i(TAG, "getCityByName: " + cityCall.request().toString());
        cityCall.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                Log.e(TAG, "onResponse: FINE");
                City city = response.body();
                if (response.isSuccessful()) {
                    onFinished(city);
                }
                else Log.i(TAG, "onResponse: ne uspeh");
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void getForecast(Double lat, Double lon) {
        Call<Forecast> cityCall = weatherServiceApi
                .getForecastByLocation(lat, lon, Constants.WEATHER_API_KEY, Constants.DEFAULT_UNITS);
        Log.i(TAG, "getCityByName: " + cityCall.request().toString());
        cityCall.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                Log.e(TAG, "onResponse: FINE");
                Forecast forecast = response.body();
                if (response.isSuccessful()) {
                    if (forecast != null) {
                        onFinished(forecast);
                    }
                    Log.i(TAG, "onResponse: NULL EXAMPLE");
                }
                else Log.i(TAG, "onResponse: ne uspeh");
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void getCurrentLocation() {
        locationService.getLastLocation()
                .subscribe(location ->
        {
            Log.i(TAG, "getCurrentLocation: " + location);
            Double lat = location.getLatitude();
            Double lon = location.getLongitude();
            getCity(lat, lon);
            getForecast(lat, lon);
        });
    }

//    @Override
//    public void updateLocation() {
//        locationService.updateLocation()
//                .subscribe(location ->
//                        {
//                            Log.i(TAG, "updateLocation: " + location);
//                            Double lat = location.getLatitude();
//                            Double lon = location.getLongitude();
//                            getCity(lat, lon);
//                        }
//                );
//    }

    @Override
    public void onResume() {
        if (cityView != null) {
            cityView.showProgress();
        }
        getCurrentLocation();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onFinished(City city) {
        if (cityView != null) {
            cityView.setCity(new CityAdapter(city));
            cityView.hideProgress();
        }
    }

    @Override
    public void onFinished(Forecast forecast) {
        if (cityView != null) {
            cityView.setForecast(convertForecast(forecast));
            cityView.hideProgress();
        }
    }

    private List<ForecastItemAdapter> convertForecast(Forecast forecast) {
        List<ForecastItemAdapter> list = new ArrayList<>();
        for (ForecastItems item : forecast.getForecastList()) {
            Long date = item.getDt();
            String description = item.getWeather().get(0).getDescription();
            String icon = item.getWeather().get(0).getIcon();
            String temp = item.getMain().getTemp().toString();
            String wind = item.getWind().getSpeed().toString();
            Double rain = item.getRain() != null ? item.getRain().get3h() : null;
            Double snow = item.getSnow() != null ? item.getSnow().get3h() : null;

            list.add(new ForecastItemAdapter(
                    date, description, icon, temp, wind, rain, snow));
        }
        return list;
    }
}
