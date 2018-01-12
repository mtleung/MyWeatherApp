package com.example.myweatherapp.search;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myweatherapp.R;
import com.example.myweatherapp.databinding.ActivitySearchBinding;
import com.example.myweatherapp.di.ViewModelFactory;
import com.example.myweatherapp.models.WeatherMain;

public class SearchActivity extends AppCompatActivity {
    private final String TAG = SearchActivity.class.getSimpleName();

    private ActivitySearchBinding binding;
    private SearchViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = obtainViewModel(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.setViewModel(viewModel);

        initViewModel();
        initInputListener();
    }

    private void initViewModel() {
        viewModel.getWeather().observe(this, (weatherMain -> {
            if (weatherMain == null) {
                return;
            }
            if (weatherMain.getError() != null) {
                showWarning(String.format("%s: %s", weatherMain.getError().getStatusCode(),
                        weatherMain.getError().getMessage()));
                binding.containerWeather.setVisibility(View.GONE);
                binding.txtNoCity.setVisibility(View.VISIBLE);
            } else {
                setUI(weatherMain.getData());
                binding.btnGetHistory.setEnabled(true);
            }
        }));

        viewModel.getErrors().observe(this, errorMsg -> {
            if (errorMsg != null) {
                showWarning(errorMsg);
            }
        });

        viewModel.getCityName().observe(this, cityName -> {
            boolean hasCity = cityName != null && !cityName.isEmpty();
            Log.d(TAG, String.format("CityName: %s (%s)", cityName, hasCity));
            binding.txtNoCity.setVisibility(hasCity ? View.GONE : View.VISIBLE);
            if (hasCity) {
                return;
//                binding.editCity.setText(cityName);
            } else {
                binding.btnGetHistory.setEnabled(false);
            }
        });

        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading == null) {
               binding.progressBar.setVisibility(View.GONE);
               return;
            }
            if (isLoading) {
                binding.btnGetHistory.setEnabled(false);
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.containerWeather.setVisibility(View.GONE);
                return;
            }
            binding.progressBar.setVisibility(View.GONE);
        });
    }

    private void setUI(WeatherMain weatherMain) {
        binding.containerWeather.setVisibility(View.VISIBLE);
        binding.txtCity.setText(weatherMain.getCity());
        binding.txtMain.setText(weatherMain.getMain());
        binding.txtDescription.setText(weatherMain.getDescription());
        binding.txtMin.setText(String.valueOf(weatherMain.getTempMin()));
        binding.txtMax.setText(String.valueOf(weatherMain.getTempMax()));
        Glide.with(this).load(weatherMain.getIconUrl()).into(binding.imgIcon);
    }

    private void showWarning(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void initInputListener() {
        binding.btnGetCity.setOnClickListener((view) -> {
            String city = binding.editCity.getText().toString().trim();
            if (city.isEmpty()) {
                Toast.makeText(this, getString(R.string.empty_city), Toast.LENGTH_SHORT).show();
                return;
            }
            dismissKeyboard(view.getWindowToken());
            viewModel.weatherByCityName(city);
        });
    }

    private SearchViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(SearchViewModel.class);
    }

    private void dismissKeyboard(IBinder windowToken) {
        binding.editCity.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(windowToken, 0);
        }
    }
}
