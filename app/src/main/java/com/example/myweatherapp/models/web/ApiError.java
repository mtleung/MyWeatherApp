package com.example.myweatherapp.models.web;

/**
 * Created by marco.t.leung on 10/1/2018.
 */

public class ApiError {
    int statusCode;
    String message;

    public ApiError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
