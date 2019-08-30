package com.example.weather.ApiService;

import android.net.Uri;
import android.util.Log;

import com.example.weather.BuildConfig;
import com.example.weather.Model.City;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiService implements IApiService{

    public static final String TAG = "api";
    private static final String OPENWEATHERMAP_WEATHER = "http://api.openweathermap.org/data/2.5/weather";
    private static final String OPENWEATHERMAP_FIND = "http://api.openweathermap.org/data/2.5/find";
    private static final String APIKEY = BuildConfig.WEATHER_API_KEY;

    @Override
    public City getCity(String cityName) {
        City city = new City();
        String url = Uri.parse(OPENWEATHERMAP_WEATHER)
                .buildUpon()
                .appendQueryParameter("q", cityName)
                .appendQueryParameter("appid", APIKEY)
                .appendQueryParameter("units", "metric")
                .appendQueryParameter("lang", "ru")
                .build().toString();
        Log.i(TAG, "getCity: " + url);
        try {
            String jsonString = getUrlString(url);
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonString);
            Gson gson = new Gson();
            Type listType = new TypeToken<City>() {
            }.getType();
            city = gson.fromJson(jsonObject, listType);
            Log.i(TAG, "getCity: String: " + city.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "getCity: ", e);
        }
        return city;
    }

    @Override
    public City getCity(Integer cityId) {
        City city = new City();
        String url = Uri.parse(OPENWEATHERMAP_WEATHER)
                .buildUpon()
                .appendQueryParameter("id", cityId.toString())
                .appendQueryParameter("appid", "45594d459bbf67a0216e10fca10985e9")
                .appendQueryParameter("units", "metric")
                .appendQueryParameter("lang", "ru")
                .build().toString();
        Log.i(TAG, "getCity: " + url);
        try {
            String jsonString = getUrlString(url);
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonString);
            Gson gson = new Gson();
            Type listType = new TypeToken<City>() {
            }.getType();
            city = gson.fromJson(jsonObject, listType);
            Log.i(TAG, "getCity: String: " + city.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "getCity: ", e);
        }
        return city;
    }

    @Override
    public City getCity(Double lat, Double lon) {
        City city = new City();
        String url = Uri.parse(OPENWEATHERMAP_WEATHER)
                .buildUpon()
                .appendQueryParameter("lat", lat.toString())
                .appendQueryParameter("lon", lon.toString())
                .appendQueryParameter("appid", "45594d459bbf67a0216e10fca10985e9")
                .appendQueryParameter("units", "metric")
                .appendQueryParameter("lang", "ru")
                .build().toString();
        Log.i(TAG, "getCity: " + url);
        try {
            String jsonString = getUrlString(url);
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonString);
            Gson gson = new Gson();
            Type listType = new TypeToken<City>() {
            }.getType();
            city = gson.fromJson(jsonObject, listType);
            Log.i(TAG, "getCity: String: " + city.toString());
        } catch (IOException e) {
            
            Log.e(TAG, "getCity:asd ", e);
        }
        return city;
    }

    @Override
    public List<City> getCityList(String cityName) {
        List<City> cityList = new ArrayList<>();
        String url = Uri.parse(OPENWEATHERMAP_FIND)
                .buildUpon()
                .appendQueryParameter("q", cityName)
                .appendQueryParameter("appid", "45594d459bbf67a0216e10fca10985e9")
                .appendQueryParameter("units", "metric")
                .appendQueryParameter("lang", "ru")
                .build().toString();
        Log.i(TAG, "getCity: " + url);
        try {
            String jsonString = getUrlString(url);
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonString);
            JsonElement jsonElement = jsonObject.get("list");
            Gson gson = new Gson();
            Type listType = new TypeToken<List<City>>() {
            }.getType();
            cityList = gson.fromJson(jsonElement, listType);

        } catch (IOException e) {
            Log.e(TAG, "getCity: getList ", e);
        }
        return cityList;
    }


    private byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {

                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();

            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }


    private String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

}
