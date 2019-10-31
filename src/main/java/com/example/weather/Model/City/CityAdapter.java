package com.example.weather.Model.City;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CityAdapter {
    private String name;
    private String date;
    private String temp;
    private String wind;
    private String precipitation;
    private String icon;

    public CityAdapter(final City city) {
        this.name = city.getName();
        this.date = getDateFormatted();
        this.temp = city.getMain().getTemp().toString();
        this.wind = city.getWind().toString();
        this.icon = "http://openweathermap.org/img/wn/" +
                city.getWeather().get(0).getIcon() +
                "@2x.png";
    }

    private String getDateFormatted() {
        SimpleDateFormat format = new SimpleDateFormat("EE, dd MMMM, HH:mm");
        return format.format(new Date());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}


