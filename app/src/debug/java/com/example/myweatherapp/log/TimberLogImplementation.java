package com.example.myweatherapp.log;


import android.support.annotation.Nullable;

import timber.log.Timber;

/**
 * Created by marco.t.leung on 26/3/2018.
 */

public class TimberLogImplementation implements TimberLog {
    public static void init() {
        Timber.plant(new Timber.DebugTree());
    }
}
