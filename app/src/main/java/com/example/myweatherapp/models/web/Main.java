package com.example.myweatherapp.models.web;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marco.t.leung on 10/1/2018.
 */

public class Main {
    private double temp;
    private float pressure;
    private float humidity;
    @SerializedName("temp_min")
    private double tempMin;
    @SerializedName("temp_max")
    private double tempMax;

    public double getTemp() {
        return temp;
    }

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }
}
