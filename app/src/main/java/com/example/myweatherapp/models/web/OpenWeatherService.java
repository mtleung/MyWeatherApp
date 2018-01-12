package com.example.myweatherapp.models.web;

import android.arch.lifecycle.LiveData;

/**
 * Created by marco.t.leung on 10/1/2018.
 */

public class OpenWeatherService {
    private static OpenWeatherService INSTANCE;

    private final String openWeatherId;
    private final OpenWeatherApi api;

    public static OpenWeatherService getInstance(String openWeatherId, OpenWeatherApi api) {
        if (INSTANCE == null) {
            INSTANCE = new OpenWeatherService(openWeatherId, api);
        }
        return INSTANCE;
    }

    private OpenWeatherService(String openWeatherId, OpenWeatherApi api) {
        this.openWeatherId = openWeatherId;
        this.api = api;
    }

    public LiveData<ApiResponse<WeatherResponse>> getWeatherByCity(String city) {
        return api.getWeatherByCity(openWeatherId, city, "metric");
    }
}
