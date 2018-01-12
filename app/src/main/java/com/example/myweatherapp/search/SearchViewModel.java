package com.example.myweatherapp.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.myweatherapp.models.WeatherMain;
import com.example.myweatherapp.models.web.ApiError;
import com.example.myweatherapp.models.web.ApiResponse;
import com.example.myweatherapp.models.web.WeatherResponse;
import com.example.myweatherapp.repository.WeatherRepository;

/**
 * Created by marco.t.leung on 10/1/2018.
 */

public class SearchViewModel extends ViewModel {
    private final String TAG = SearchViewModel.class.getSimpleName();

    private final WeatherRepository repository;

    // User input city name
    public MutableLiveData<String> cityName = new MutableLiveData<>();

    // Api Error
    private final MediatorLiveData<Boolean> isLoading = new MediatorLiveData<>();
    private final MutableLiveData<String> errors = new MutableLiveData<>();

    // LiveData observed by view
    private MediatorLiveData<ApiResponse<WeatherMain>> weatherData = new MediatorLiveData<>();

    // LiveData returned from WeatherRepository
    private LiveData<ApiResponse<WeatherResponse>> weatherByCityResponse;


    public SearchViewModel(WeatherRepository repository) {
        Log.d(TAG, "Constructor");
        this.repository = repository;
        addWeatherSources();
        initValues();
    }

    void weatherByCityName(String city) {
        Log.d(TAG,"weatherByCityName " + city);
        cityName.postValue(city);
    }

    @Override
    protected void onCleared() {
        Log.d(TAG, "onCleared");
        weatherData.removeSource(weatherByCityResponse);
        super.onCleared();
    }

    private void initValues() {
        cityName.setValue(repository.getLastCity());
    }

    private void addWeatherSources() {
        weatherByCityResponse = Transformations.switchMap(cityName, (name) -> {
            weatherData.setValue(null);
            if (name == null || name.isEmpty()) {
                return null;
            }
            return repository.getWeatherByCity(name);
        });

        weatherData.addSource(weatherByCityResponse, this::processWeatherResponse);

        isLoading.addSource(cityName, (name) -> {
            updateLoadingStatus();
        });
        isLoading.addSource(weatherData, (weatherData) -> {
            updateLoadingStatus();
        });
    }

    LiveData<ApiResponse<WeatherMain>> getWeather() {
        return weatherData;
    }

    LiveData<String> getErrors() {
        return errors;
    }

    LiveData<String> getCityName() {
        return cityName;
    }

    LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private void updateLoadingStatus() {
        // No city name
        if (cityName.getValue() == null || cityName.getValue().trim().isEmpty()) {
            isLoading.setValue(false);
            return;
        }
        // No weather data
        isLoading.setValue(weatherData.getValue() == null);
    }

    private void processWeatherResponse(ApiResponse<WeatherResponse> apiResponse) {
        if (apiResponse == null || apiResponse.getData() == null) {
            // Error
            errors.setValue(String.format("Failed to fetch weather for %s", cityName.getValue()));
            if (apiResponse != null && apiResponse.getError() != null) {
                weatherData.setValue(new ApiResponse<>(null, apiResponse.getError()));
                return;
            }
            weatherData.setValue(new ApiResponse<>(null, new ApiError(500, "Something went wrong!")));
            return;
        }
        repository.setLastCity(cityName.getValue());

        WeatherMain weatherMain = WeatherMain.factory(apiResponse.getData());
        weatherData.setValue(new ApiResponse(weatherMain, null));
    }
}
