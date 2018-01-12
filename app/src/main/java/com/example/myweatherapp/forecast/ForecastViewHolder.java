package com.example.myweatherapp.forecast;

import android.support.v7.widget.RecyclerView;

import com.example.myweatherapp.databinding.ItemForecastBinding;
import com.example.myweatherapp.models.ForecastMain;

/**
 * Created by marco.t.leung on 12/1/2018.
 */

public class ForecastViewHolder extends RecyclerView.ViewHolder {
    private final ItemForecastBinding binding;

    public ForecastViewHolder(ItemForecastBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(ForecastMain forecast) {
        binding.setForecast(forecast);
        binding.executePendingBindings();
    }
}
