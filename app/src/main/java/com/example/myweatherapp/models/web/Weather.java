package com.example.myweatherapp.models.web;

/**
 * Created by marco.t.leung on 10/1/2018.
 */

public class Weather {
    private long id;
    private String main;
    private String description;
    private String icon;

    public long getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
