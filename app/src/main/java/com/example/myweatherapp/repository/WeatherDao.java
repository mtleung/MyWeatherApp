package com.example.myweatherapp.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.myweatherapp.models.WeatherMain;

/**
 * Created by marco.t.leung on 10/1/2018.
 */
@Dao
public interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeatherItem(WeatherMain weather);

    @Query("SELECT * FROM weather WHERE city = :city")
    LiveData<WeatherMain> getWeather(String city);

    @Query("DELETE FROM weather")
    void clearWeather();
}
