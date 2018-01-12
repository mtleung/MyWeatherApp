package com.example.myweatherapp.di;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.myweatherapp.forecast.ForecastViewModel;
import com.example.myweatherapp.repository.WeatherRepository;
import com.example.myweatherapp.search.SearchViewModel;

/**
 * Created by marco.t.leung on 10/1/2018.
 */

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;

    private final Application application;
    private final WeatherRepository weatherRepository;

    public static ViewModelFactory getInstance(Application application){
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application,
                            Injection.provideWeatherRepository(application));
                }
            }
        }
        return INSTANCE;
    }

    private ViewModelFactory(Application application, WeatherRepository weatherRepository) {
        this.application = application;
        this.weatherRepository = weatherRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            //noinspection unchecked
            return (T) new SearchViewModel(weatherRepository);
        }
        if (modelClass.isAssignableFrom(ForecastViewModel.class)) {
            return (T) new ForecastViewModel(weatherRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class " + modelClass.getName());
    }
}
