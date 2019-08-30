package com.example.weather;

import com.example.weather.ApiService.ApiService;
import com.example.weather.Model.City;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void cityTest (){
        String cityName = "London";
        ApiService mApiService = new ApiService();
        Observable.create((ObservableOnSubscribe<City>) subscriber ->
            subscriber.onNext(mApiService.getCity(cityName)))
                    .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(city -> {

                    assertFalse(city.getName().equals("lond0n"));
                });
    }
}