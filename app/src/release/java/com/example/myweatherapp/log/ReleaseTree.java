package com.example.myweatherapp.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import timber.log.Timber;

import static android.util.Log.ERROR;
import static android.util.Log.WARN;

/**
 * Created by marco.t.leung on 26/3/2018.
 */

public class ReleaseTree extends Timber.Tree {
    @Override
    protected void log(int priority, @Nullable String tag, @NonNull String message, @Nullable Throwable t) {
        if (priority == ERROR || priority == WARN) {
            // TODO - Crashlytics log
        }
    }
}
