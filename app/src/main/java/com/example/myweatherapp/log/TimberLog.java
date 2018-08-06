package com.example.myweatherapp.log;

import java.util.Map;

/**
 * Created by marco.t.leung on 26/3/2018.
 */

public interface TimberLog {
    static void init(){};
    static void init(final Map<String, String> userInformation){};
}
