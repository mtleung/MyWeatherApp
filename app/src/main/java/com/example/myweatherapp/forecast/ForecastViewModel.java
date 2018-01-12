package com.example.myweatherapp.forecast;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.myweatherapp.models.ForecastMain;
import com.example.myweatherapp.models.web.ApiError;
import com.example.myweatherapp.models.web.ApiResponse;
import com.example.myweatherapp.models.web.Forecast;
import com.example.myweatherapp.models.web.ForecastResponse;
import com.example.myweatherapp.repository.WeatherRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco.t.leung on 12/1/2018.
 */

public class ForecastViewModel extends ViewModel {
    private final String TAG = ForecastViewModel.class.getSimpleName();
    private final WeatherRepository repository;

    // LiveData observed by view
    private MediatorLiveData<ApiResponse<List<ForecastMain>>> forecastData = new MediatorLiveData<>();
    private LiveData<Boolean> isLoading;

    // LiveData returned from WeatherRepository
    public MutableLiveData<String> cityName = new MutableLiveData<>();
    private LiveData<ApiResponse<ForecastResponse>> forecastByCityResponse;

    public ForecastViewModel(WeatherRepository repository) {
        this.repository = repository;
        addWeatherSources();
        initValues();
    }

    LiveData<ApiResponse<List<ForecastMain>>> getForecastData() {
        return forecastData;
    }

    LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private void addWeatherSources() {
        forecastByCityResponse = Transformations.switchMap(cityName, repository::getForecastByCity);

        forecastData.addSource(forecastByCityResponse, this::processForecastResponse);

        isLoading = Transformations.switchMap(forecastData, forecast -> {
            boolean status = forecast.getData() == null && forecast.getError() == null;

            Log.d(TAG, String.format("isLoading %s (%s) (%s)",
                    status, forecast.getData() != null, forecast.getError() != null));

            MutableLiveData<Boolean> output = new MutableLiveData<>();
            output.setValue(status);
            return output;
        });
    }

    private void initValues() {
        cityName.setValue(repository.getLastCity());
    }

    private void processForecastResponse(ApiResponse<ForecastResponse> apiResponse) {
        if (apiResponse == null || apiResponse.getData() == null) {
            // Error
            if (apiResponse != null && apiResponse.getError() != null) {
                forecastData.setValue(new ApiResponse<>(null, apiResponse.getError()));
                return;
            }
            forecastData.setValue(new ApiResponse<>(null, new ApiError(500, "Something went wrong!")));
            return;
        }

        List<Forecast> forecast = apiResponse.getData().getForecast();
        List<ForecastMain> output = new ArrayList<>();
        for (Forecast fc : forecast) {
            output.add(ForecastMain.factory(fc));
        }
        forecastData.setValue(new ApiResponse<>(output, null));
    }
}
