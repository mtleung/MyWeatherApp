package com.example.myweatherapp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.myweatherapp.models.web.WeatherResponse;

/**
 * Created by marco.t.leung on 10/1/2018.
 */

@Entity(tableName = "weather")
public class WeatherMain {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "date")
    private Long dt;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "temp_min")
    private double tempMin;

    @ColumnInfo(name = "temp_max")
    private double tempMax;

    @ColumnInfo(name = "main")
    private String main;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "icon")
    private String icon;

    public WeatherMain(){}

    public static WeatherMain factory(WeatherResponse response) {
        return new WeatherMain(response);
    }

    public WeatherMain(WeatherResponse response) {
        this.dt = response.getDt();
        this.city = response.getName();
        this.tempMin = response.getMain().getTempMin();
        this.tempMax = response.getMain().getTempMax();

        if (response.getWeather().size() > 0) {
            this.main = response.getWeather().get(0).getMain();
            this.description = response.getWeather().get(0).getDescription();
            this.icon = response.getWeather().get(0).getIcon();
        }
    }

    public String getIconUrl() {
        return String.format("http://openweathermap.org/img/w/%s.png", icon);
    }

    public long getId() {
        return id;
    }

    public Long getDt() {
        return dt;
    }

    public String getCity() {
        return city;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
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

    public void setCity(String city) {
        this.city = city;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
