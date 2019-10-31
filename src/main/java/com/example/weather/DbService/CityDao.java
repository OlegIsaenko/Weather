package com.example.weather.DbService;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.weather.Model.City.City;
import java.util.List;

@Dao
public interface CityDao {
    @Query("SELECT * FROM City")
    List<City> getAll();

    @Query("SELECT * FROM City WHERE id = :id")
    City getById(Integer id);

    @Insert
    void insert(City city);

    @Update
    void update(City city);

    @Delete
    void delete(City city);
}
