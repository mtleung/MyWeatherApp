package com.example.myweatherapp.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.myweatherapp.models.WeatherMain;

/**
 * Created by marco.t.leung on 10/1/2018.
 */
@Database(entities = {WeatherMain.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {
    private static WeatherDatabase INSTANCE;

    public abstract WeatherDao weatherDao();

    private static final Object sLock = new Object();

    public static WeatherDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        WeatherDatabase.class, "WeatherDatabase.db")
                        .build();
            }
            return INSTANCE;
        }
    }
}
