package com.example.myweatherapp.forecast;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.myweatherapp.R;
import com.example.myweatherapp.databinding.ItemForecastBinding;
import com.example.myweatherapp.models.ForecastMain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco.t.leung on 12/1/2018.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastViewHolder> {
    private ArrayList<ForecastMain> mForecasts = new ArrayList<>();



    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemForecastBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.item_forecast, parent, false);
        return new ForecastViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        holder.bind(mForecasts.get(position));
    }

    @Override
    public int getItemCount() {
        return mForecasts.size();
    }

    public void setForecasts(List<ForecastMain> forecasts) {
        mForecasts.clear();
        mForecasts.addAll(forecasts);
    }
}
