package com.example.myweatherapp.models.web;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by marco.t.leung on 12/1/2018.
 */

public class ForecastResponse {
    private City city;
    private Coord coord;
    private String country;
    @SerializedName("list")
    private ArrayList<Forecast> forecast;
    private int cod;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<Forecast> getForecast() {
        return forecast;
    }

    public void setForecast(ArrayList<Forecast> forecast) {
        this.forecast = forecast;
    }
}
