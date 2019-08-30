package com.example.weather.View;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.Model.City;
import com.example.weather.Presenter.ICityViewPresenter;
import com.example.weather.Presenter.CityViewPresenter;
import com.example.weather.R;
import com.squareup.picasso.Picasso;

import static com.example.weather.ApiService.ApiService.TAG;

public class CityViewFragment extends Fragment implements ICityViewFragment {

    public static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    private ICityViewPresenter mWeatherPresenter;
    private TextView cityDescription;
    private TextView message;
    private ImageView condIcon;
    private Button findCityButton;
    private Button lastLocationButton;
    private Button updateLocationButton;
    private Button searchButton;
    private EditText cityName;


    public CityViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mWeatherPresenter == null) {
            mWeatherPresenter = new CityViewPresenter(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_view, container, false);
        cityName = view.findViewById(R.id.city_name_edit_text);
        cityDescription = view.findViewById(R.id.city_fragment_text);
        condIcon = view.findViewById(R.id.condition_icon);

        findCityButton = view.findViewById(R.id.find_city_button);
        findCityButton.setOnClickListener(v ->
                mWeatherPresenter.getCity(cityName.getText().toString()));

        lastLocationButton = view.findViewById(R.id.location_button);
        lastLocationButton.setOnClickListener(v -> getCity());

        updateLocationButton = view.findViewById(R.id.location_update_button);
        updateLocationButton.setOnClickListener(v ->
                mWeatherPresenter.updateLocation(getActivity()));

        searchButton = view.findViewById(R.id.search_city_button);
        searchButton.setOnClickListener(v ->
                mWeatherPresenter.getCityList(cityName.getText().toString()));

        message = view.findViewById(R.id.message_text);

        return view;
    }

    private void getCity() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mWeatherPresenter.getCurrentCity(getActivity());
        }
        else {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        }
    }

    @Override
    public void onError(String text) {
        message.setText(text);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "requestPermission: GRANTED");
                    mWeatherPresenter.getCurrentCity(getActivity());
                } else {
                    Log.i(TAG, "onRequestPermissionsResult: hyi a ne permission");
                    mWeatherPresenter.getCity("saint petersburg");
                }
                return;
            }
        }
    }

    @Override
    public void showCity(City city) {
        cityDescription.setText(city.toString());
        Picasso.get().load("http://openweathermap.org/img/wn/" +
                city.getWeather().get(0).getIcon() +
                "@2x.png")
                .into(condIcon);
    }

}
