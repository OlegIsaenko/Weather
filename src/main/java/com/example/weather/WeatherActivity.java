package com.example.weather;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.weather.View.CityViewFragment;

public class WeatherActivity extends AppCompatActivity {

    public static final String TAG = "api";
    private FragmentManager mManager;
    private FragmentTransaction mTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mManager = getSupportFragmentManager();
        mTransaction = mManager.beginTransaction();
        Fragment cityFragment = new CityViewFragment();
        mTransaction.add(R.id.fragment_placeholder, cityFragment).commit();

    }
}
