package com.example.myweatherapp.models.web;

/**
 * Created by marco.t.leung on 10/1/2018.
 */

public class ApiResponse<T> {
    private T data;
    private ApiError error;

    public ApiResponse(T data, ApiError error) {
        this.data = data;
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public ApiError getError() {
        return error;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ApiResponse{");
        sb.append("data:");
        if (data != null) {
            sb.append(data.toString());
        } else {
            sb.append("NoData");
        }
        sb.append(",error:");
        if (error != null) {
            sb.append(error.toString());
        } else {
            sb.append("NoError");
        }
        sb.append("}");
        return sb.toString();
    }
}
