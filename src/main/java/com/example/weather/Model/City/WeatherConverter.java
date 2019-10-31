package com.example.weather.Model.City;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeatherConverter {
    @TypeConverter
    public String fromWeather(List<Weather> weathers) {
        String result = "";
        for (Weather weather :
                weathers) {
            result = weather.getId() + "," +
                    weather.getMain() + "," +
                    weather.getDescription() + "," +
                    weather.getIcon() + "\n";
        }
        return result;
    }

    @TypeConverter
    public List<Weather> toWeather(String data) {
        List<Weather> weather = new ArrayList<>();
        List<String> temp;
        temp = Arrays.asList(data.split(","));
        Weather main = new Weather();
        main.setId(Integer.parseInt(temp.get(0)));
        main.setMain(temp.get(1));
        main.setDescription(temp.get(2));
        main.setIcon(temp.get(3));
        weather.add(main);
        return weather;
    }
}
