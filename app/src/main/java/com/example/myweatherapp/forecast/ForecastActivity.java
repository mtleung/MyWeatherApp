package com.example.myweatherapp.forecast;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myweatherapp.R;
import com.example.myweatherapp.databinding.ActivityForecastBinding;
import com.example.myweatherapp.di.ViewModelFactory;

import timber.log.Timber;

/**
 * Created by marco.t.leung on 12/1/2018.
 */

public class ForecastActivity extends AppCompatActivity {
    private final String TAG = ForecastActivity.class.getSimpleName();

    private ActivityForecastBinding binding;
    private ForecastViewModel viewModel;

    private ForecastAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = obtainViewModel(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forecast);
        binding.setViewModel(viewModel);

        initView();
        initViewModel();
    }

    private void initView() {
        adapter = new ForecastAdapter();
        binding.forecastList.setLayoutManager(new LinearLayoutManager(this));
        binding.forecastList.setAdapter(adapter);
    }

    private void initViewModel() {
        viewModel.getForecastData().observe(this, response -> {
            Timber.d( "Forecast updated");
            if (response == null) {
                Timber.d("No Response");
                binding.forecastList.setVisibility(View.GONE);
                binding.forecastError.setVisibility(View.VISIBLE);
                return;
            }
            // Error case
            if (response.getError() != null) {
                Timber.d("Forecast error");
                showWarning(String.format("%s: %s",
                        response.getError().getStatusCode(),
                        response.getError().getMessage()));
                binding.forecastError.setVisibility(View.VISIBLE);
                binding.forecastList.setVisibility(View.GONE);
                return;
            }

            if (response.getData() == null) {
                Timber.d("No Data");
                binding.forecastError.setVisibility(View.VISIBLE);
                binding.forecastList.setVisibility(View.GONE);
                return;
            }
            // Normal case
            Timber.d("Forecast has data");
            binding.forecastError.setVisibility(View.GONE);
            binding.forecastList.setVisibility(View.VISIBLE);

            adapter.setForecasts(response.getData());
            adapter.notifyDataSetChanged();
        });

        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading == null) {
                binding.forecastLoading.setVisibility(View.GONE);
                return;
            }
            binding.forecastLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });
    }

    private void showWarning(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private ForecastViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(ForecastViewModel.class);
    }
}
