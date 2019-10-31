package com.example.weather.DbService;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.weather.Model.City.City;

@Database(entities = {City.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CityDao cityDao();
}
