package com.example.weather.Model.Forecast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ForecastItemAdapter {

    private String dayOfWeek;
    private String dayTime;
    private String description;
    private String icon;
    private String temp;
    private String wind;
    private String precipitation;

    public ForecastItemAdapter(Long date, String description, String icon, String temp, String wind, Double rain, Double snow) {
        this.dayOfWeek = getDay(getDate(date));
        this.dayTime = getTime(getDate(date));
        this.description = description;
        this.icon = "http://openweathermap.org/img/wn/" + icon + "@2x.png";
        this.temp = temp;
        this.wind = wind;
        this.precipitation = setPrecipitation(rain, snow);
    }

    private String setPrecipitation(Double rain, Double snow) {
        rain = rain == null ? 0 : rain;
        snow = snow == null ? 0 : snow;
        return String.valueOf(rain + snow);
    }


    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    private String getDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        return format.format(date);
    }

    private Date getDate(Long date) {
        return new Date(date * 1000);
    }


    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getDayTime() {
        return dayTime;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public String getTemp() {
        return temp;
    }

    public String getWind() {
        return wind;
    }

    public String getPrecipitation() {
        return precipitation;
    }

}
