package com.example.myweatherapp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.util.Log;

import com.example.myweatherapp.models.web.Forecast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by marco.t.leung on 12/1/2018.
 */

@Entity(tableName = "forecast")
public class ForecastMain {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "date")
    private long dt;

    @ColumnInfo(name = "temp")
    private double temp;

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

    private SimpleDateFormat sdf;

    public static ForecastMain factory(Forecast forecast) {
        return new ForecastMain(forecast);
    }

    private ForecastMain(Forecast forecast) {
        dt = forecast.getDt();
        sdf = new SimpleDateFormat("HH:mm d MMM", Locale.getDefault());

        if (forecast.getMain() != null) {
            temp = forecast.getMain().getTemp();
            tempMin = forecast.getMain().getTempMin();
            tempMax = forecast.getMain().getTempMax();
        }

        if (forecast.getWeather().size() > 0) {
            main = forecast.getWeather().get(0).getMain();
            description = forecast.getWeather().get(0).getDescription();
            icon = forecast.getWeather().get(0).getIcon();
        }
    }

    public String getIconUrl() {
        return String.format("http://openweathermap.org/img/w/%s.png", icon);
    }

    public String getDate() {
        return sdf.format(new Date(dt * 1000));
    }

    public double getTemp() {
        return temp;
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
}
