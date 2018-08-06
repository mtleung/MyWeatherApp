package com.example.myweatherapp.util;

/**
 * Created by marco.t.leung on 10/1/2018.
 */

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.myweatherapp.models.web.ApiError;
import com.example.myweatherapp.models.web.ApiResponse;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * A Retrofit adapter that converts the Call into a LiveData of ApiResponse.
 * @param <R>
 */
public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse<R>>> {
    private final Type responseType;
    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<ApiResponse<R>> adapt(Call<R> call) {
        return new LiveData<ApiResponse<R>>() {
            AtomicBoolean started = new AtomicBoolean(false);
            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<R>() {
                        @Override
                        public void onResponse(Call<R> call, Response<R> response) {
                            Timber.d(new Gson().toJson(response));
                            postValue(new ApiResponse(response.body(), null));
                        }

                        @Override
                        public void onFailure(Call<R> call, Throwable throwable) {
                            postValue(new ApiResponse(null,
                                    new ApiError(500, throwable.getMessage())));
                        }
                    });
                }
            }
        };
    }
}
