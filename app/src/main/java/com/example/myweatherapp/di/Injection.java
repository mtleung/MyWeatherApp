package com.example.myweatherapp.di;

import android.content.Context;

import com.example.myweatherapp.R;
import com.example.myweatherapp.models.web.OpenWeatherApi;
import com.example.myweatherapp.models.web.OpenWeatherService;
import com.example.myweatherapp.repository.SharedPrefs;
import com.example.myweatherapp.repository.WeatherRepository;
import com.example.myweatherapp.util.LiveDataCallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marco.t.leung on 10/1/2018.
 */

class Injection {
    static WeatherRepository provideWeatherRepository(Context context) {
        String openWeatherId = context.getString(R.string.openweathermap_key);
        OpenWeatherApi api = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(OpenWeatherApi.class);
        SharedPrefs sharedPrefs = SharedPrefs.getInstance(context);

        return WeatherRepository.getInstance(
                OpenWeatherService.getInstance(openWeatherId, api),
                sharedPrefs
        );
    }
}
