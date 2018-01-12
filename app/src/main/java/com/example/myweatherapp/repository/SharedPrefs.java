package com.example.myweatherapp.repository;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by marco.t.leung on 12/1/2018.
 */

public class SharedPrefs {
    private volatile static SharedPrefs INSTANCE;

    private SharedPreferences sharedPreferences;

    public static SharedPrefs getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SharedPrefs.class) {
                if (INSTANCE == null) {
                    SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(
                            "MyWeatherAppPrefs", Context.MODE_PRIVATE);
                    INSTANCE = new SharedPrefs(preferences);
                }
            }
        }
        return INSTANCE;
    }

    private SharedPrefs(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public String getValue(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void setValue(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
