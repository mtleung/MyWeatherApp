package com.example.myweatherapp.models.web;

import android.arch.lifecycle.LiveData;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by marco.t.leung on 10/1/2018.
 */

public interface OpenWeatherApi {
    @GET("weather")
    LiveData<ApiResponse<WeatherResponse>> getWeatherByCity(
            @Query("APPID") String appId,
            @Query("q") String city,
            @Query("units") String unit
    );

    @GET("forecast")
    LiveData<ApiResponse<ForecastResponse>> getForecastByCity(
            @Query("APPID") String appId,
            @Query("q")String city,
            @Query("units") String unit
    );
}
