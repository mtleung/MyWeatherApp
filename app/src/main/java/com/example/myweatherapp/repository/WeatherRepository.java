package com.example.myweatherapp.repository;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.myweatherapp.models.web.ApiResponse;
import com.example.myweatherapp.models.web.ForecastResponse;
import com.example.myweatherapp.models.web.OpenWeatherService;
import com.example.myweatherapp.models.web.WeatherResponse;

/**
 * Created by marco.t.leung on 10/1/2018.
 */

public class WeatherRepository {
    private final String TAG = WeatherRepository.class.getSimpleName();

    private volatile static WeatherRepository INSTANCE = null;

    private final OpenWeatherService openWeatherService;
    private final SharedPrefs sharedPrefs;
    private final String KEY_CITY = "MyWeatherApp.City";

    public static WeatherRepository getInstance(OpenWeatherService openWeatherService,
                                                SharedPrefs sharedPrefs) {
        if (INSTANCE == null) {
            synchronized (WeatherRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WeatherRepository(openWeatherService, sharedPrefs);
                }
            }
        }
        return INSTANCE;
    }

    private WeatherRepository(OpenWeatherService openWeatherService, SharedPrefs sharedPrefs) {
        this.openWeatherService = openWeatherService;
        this.sharedPrefs = sharedPrefs;
    }

    public LiveData<ApiResponse<WeatherResponse>> getWeatherByCity(String city) {
        return openWeatherService.getWeatherByCity(city);
    }

    public LiveData<ApiResponse<ForecastResponse>> getForecastByCity(String city) {
        return openWeatherService.getForecastByCity(city);
    }

    public String getLastCity() {
        return sharedPrefs.getValue(KEY_CITY);
    }

    public void setLastCity(String city) {
        sharedPrefs.setValue(KEY_CITY, city);
    }
}
