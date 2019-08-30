package com.example.weather.DbService;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.weather.Model.City;

@Database(entities = {City.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CityDao cityDao();
}
