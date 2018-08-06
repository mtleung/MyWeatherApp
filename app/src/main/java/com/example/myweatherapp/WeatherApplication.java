package com.example.myweatherapp;

import android.app.Application;

import com.example.myweatherapp.log.TimberLogImplementation;

/**
 * Created by marco.t.leung on 26/3/2018.
 */

public class WeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TimberLogImplementation.init();
    }
}
