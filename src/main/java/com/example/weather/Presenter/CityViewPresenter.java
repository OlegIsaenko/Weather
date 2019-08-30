package com.example.weather.Presenter;

import android.app.Activity;
import android.location.Address;
import android.location.Location;
import android.util.Log;

import com.example.weather.ApiService.ApiService;
import com.example.weather.ApiService.IApiService;
import com.example.weather.DbService.App;
import com.example.weather.DbService.AppDatabase;
import com.example.weather.LocationService.IlocationService;
import com.example.weather.LocationService.LocationService;
import com.example.weather.Model.City;
import com.example.weather.View.ICityViewFragment;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.weather.ApiService.ApiService.TAG;

public class CityViewPresenter implements ICityViewPresenter {

    private ICityViewFragment mCityViewFragment;
    private IApiService mApiService;
    private AppDatabase mDatabase;
    private IlocationService mLocation;

    public CityViewPresenter(ICityViewFragment view) {
        mCityViewFragment = view;
        mApiService = new ApiService();
        mLocation = new LocationService();
    }


    @Override
    public void getCity(String cityName) {
        Observable.create((ObservableOnSubscribe<City>) subscriber ->
                subscriber.onNext(mApiService.getCity(cityName)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(city -> {
                    if (city.getId() != null) {
                        mCityViewFragment.showCity(city);
                    }
                });
    }

    @Override
    public void getCurrentCity(Activity activity) {
        Observable.create((ObservableOnSubscribe<Observable<Location>>) subscriber ->
                subscriber.onNext(mLocation.getLastLocation(activity)))
                .subscribe(observableLocation -> observableLocation.subscribe(location ->
                            getCity(location)));
    }

    @Override
    public void updateLocation(Activity activity) {
        Observable.create((ObservableOnSubscribe<Observable<Location>>) subscriber ->
                subscriber.onNext(mLocation.updateLocation(activity)))
                .subscribe(observableLocation -> observableLocation.subscribe(location ->
                        {
                            getCity(location);
                            List<Address> addresses = mLocation.getAddressFromLocation(activity, location);
                            String result = "";
                            for (Address address :
                                    addresses) {
                                result += address.getAddressLine(0) + "\n";
                            }
                            mCityViewFragment.onError(result);
                        }
                ));
    }

    @Override
    public void getCity(Location location) {
        if (location != null) {
            Log.i(TAG, "getCity: " + location.toString());
            Double lat = location.getLatitude();
            Double lon = location.getLongitude();
            Observable.create((ObservableOnSubscribe<City>) subscriber ->
                    subscriber.onNext(mApiService.getCity(lat, lon)))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(city -> {
                        if (city.getId() != null) {
                            mCityViewFragment.showCity(city);
                        }
                    });
        }
        else {
            mCityViewFragment.onError("null loca");
            Log.i(TAG, "getCity: null");
        }
    }

    @Override
    public void getCity(Integer cityId) {

    }

    @Override
    public void getCityList(String cityName) {
        Observable.create((ObservableOnSubscribe<List<City>>) subscriber ->
                subscriber.onNext(mApiService.getCityList(cityName)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cityList -> {
                    if (cityList.size() != 0) {
                        for (City city : cityList) {
                            Log.i(TAG, "getCityList: " + city.toString());
                        }
                    }
                });
    }




    @Override
    public void getCityFromDb(Integer cityId) {
        Observable.just(getCityById(cityId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(city -> {
                    mCityViewFragment.showCity(city);
                });
    }

    @Override
    public void updateCity() {

    }



    private City getCityById(Integer id) {
        mDatabase = App.getInstance().getDatabase();
        City city = mDatabase.cityDao().getById(id);
        return city;
    }

}
