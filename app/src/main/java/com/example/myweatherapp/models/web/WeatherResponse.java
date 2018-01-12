package com.example.myweatherapp.models.web;

import java.util.List;

/**
 * Created by marco.t.leung on 10/1/2018.
 */

public class WeatherResponse {
    private Coord coord;
    private Sys sys;
    private List<Weather> weather;
    private Main main;
    private Wind wind;
    private Rain rain;
    private Clouds clouds;
    private long dt;
    private long id;
    private String name;
    private int cod;

    public Coord getCoord() {
        return coord;
    }

    public Sys getSys() {
        return sys;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Rain getRain() {
        return rain;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public long getDt() {
        return dt;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCod() {
        return cod;
    }
}
