package com.example.weather.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.Model.Forecast.ForecastItemAdapter;
import com.example.weather.Model.City.CityAdapter;
import com.example.weather.Presenter.CityViewPresenter;
import com.example.weather.Presenter.ICityViewPresenter;
import com.example.weather.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CityViewActivity extends AppCompatActivity implements CityView {

    public static final String TAG = "api";
    public static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    private ICityViewPresenter mWeatherPresenter;
    private TextView temperature;
    private ImageView condIcon;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView cityName;
    private TextView date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mWeatherPresenter = new CityViewPresenter(this);

        toolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);

        cityName = findViewById(R.id.city_name);
        date = findViewById(R.id.date);

        temperature = findViewById(R.id.obj_temp);
        condIcon = findViewById(R.id.condition_icon);

        recyclerView = findViewById(R.id.forecast);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        progressBar = findViewById(R.id.progress);

    }


    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
        mWeatherPresenter.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mWeatherPresenter.getCity(query);
                mWeatherPresenter.getForecast(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setForecast(List<ForecastItemAdapter> forecast) {
        recyclerView.setAdapter(new ForecastAdapter(forecast));
    }

    @Override
    public void setCity(CityAdapter city) {
        cityName.setText(city.getName());
        date.setText(city.getDate());
        temperature.setText(String.format(
                getResources().getString(R.string.temperature_C), city.getTemp()));
        Picasso.get().load(city.getIcon()).into(condIcon);
        toolbar.setTitle(city.getName());
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mWeatherPresenter.onResume();
        }
        else {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mWeatherPresenter.getCurrentLocation();
                } else {
                    mWeatherPresenter.getCity("saint petersburg");
                }
                return;
            }
        }
    }

    private class ForecastHolder extends RecyclerView.ViewHolder {

        private TextView day;
        private ImageView icon;
        private TextView time;
        private TextView temp;
        private TextView desc;
        private TextView wind;
        private TextView precipitation;


        ForecastHolder(View forecastView) {
            super(forecastView);
            day = forecastView.findViewById(R.id.day_of_week);
            icon = forecastView.findViewById(R.id.forecast_icon);
            time = forecastView.findViewById(R.id.time_of_day);
            temp = forecastView.findViewById(R.id.temperature);
            desc = forecastView.findViewById(R.id.description);
            wind = forecastView.findViewById(R.id.wind);
            precipitation = forecastView.findViewById(R.id.precipitation);
        }

        void bindItems(ForecastItemAdapter forecast) {
            Picasso.get().load(forecast.getIcon()).into(icon);
            day.setText(forecast.getDayOfWeek());
            time.setText(forecast.getDayTime());
            temp.setText(String.format(
                    getResources().getString(R.string.temperature_C), forecast.getTemp()));
            desc.setText(forecast.getDescription());
            wind.setText(String.format(
                    getResources().getString(R.string.wind_speed_ms), forecast.getWind()));
            precipitation.setText(String.format(
                    getResources().getString(R.string.precipitations_mm), forecast.getPrecipitation()));
        }
    }


    private class ForecastAdapter extends RecyclerView.Adapter<ForecastHolder> {

        private List<ForecastItemAdapter> forecastItems;

        ForecastAdapter(List<ForecastItemAdapter> forecast) {
            forecastItems = forecast;
        }

        @NonNull
        @Override
        public ForecastHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.forecast_holder, viewGroup, false);
            return new ForecastHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ForecastHolder holder, int position) {
            ForecastItemAdapter item = forecastItems.get(position);
            holder.bindItems(item);
        }

        @Override
        public void onViewAttachedToWindow(@NonNull ForecastHolder holder) {
//            final int adapterPosition = holder.getAdapterPosition();
//            holder.itemView.setOnClickListener(v -> {
//                if (presenter.isOnline(AlbumListView.this)) {
//                    int album_id = forecastItems.get(adapterPosition).getCollectionId();
//                    Intent intent = new Intent(AlbumListView.this, AlbumView.class);
//                    intent.putExtra(AlbumView.ALBUM_ID, album_id);
//                    startActivity(intent);
//                }
//                else  {
//                    showError();
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return forecastItems.size();
        }
    }

}

