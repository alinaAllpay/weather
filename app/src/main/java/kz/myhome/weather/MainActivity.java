package kz.myhome.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kz.myhome.weather.model.CityWeather;
import kz.myhome.weather.model.place.GooglePlacesResponse;
import kz.myhome.weather.model.place.Prediction;
import kz.myhome.weather.model.weather.OpenWeatherResponse;
import kz.myhome.weather.service.PlaceAPIService;
import kz.myhome.weather.service.ServiceFactory;
import kz.myhome.weather.service.WeatherAPIService;
import retrofit2.Retrofit;

/**
 * Created by Alina.Sibiryakova on 28.04.2018.
 */

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private EditText cityName;
    private Retrofit retrofitPlaces;
    private Retrofit retrofitWeather;
    private List<CityWeather> cityWeatherList = new ArrayList<>();
    private RecyclerView cityWeatherRecyclerView;
    private CityWeatherAdapter cityWeatherAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityWeatherRecyclerView =  findViewById(R.id.city_weather_rv);
        RecyclerView.LayoutManager couponLayoutManager = new LinearLayoutManager(this);
        cityWeatherRecyclerView.setLayoutManager(couponLayoutManager);
        cityWeatherAdapter = new CityWeatherAdapter(new ArrayList<>());
        cityWeatherRecyclerView.setAdapter(cityWeatherAdapter);

        cityName = findViewById(R.id.city_name);
        cityName.addTextChangedListener(new TextWatcher() {
            private Boolean textWatcherTogle = false; // I use this variable, because due to unknown for me reason
                                            // afterTextChanged fires multiple times for the same editable string
                                            // and it leads to extra call to Google places API
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (textWatcherTogle) {
                    final long delayBeforeStartSearch = 300;
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(() -> {
                                if (editable.length() >= 2) {
                                    startSearchCity(editable);
                                }
                            });

                        }
                    }, delayBeforeStartSearch);
                }
                textWatcherTogle = !textWatcherTogle;
        }
        });

        retrofitPlaces = ServiceFactory.createRetrofitService(PlaceAPIService.SERVICE_ENDPOINT);
        retrofitWeather = ServiceFactory.createRetrofitService(WeatherAPIService.SERVICE_ENDPOINT);
    }

    private void startSearchCity(Editable editable) {
        Log.d(TAG, "startSearchCity: ");
        cityWeatherList.clear();

        retrofitPlaces.create(PlaceAPIService.class).getCities(editable.toString())
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .map(GooglePlacesResponse::getPredictions)
                .flatMapIterable(predictions -> predictions)
                .map(Prediction::getStructuredFormatting)
                .concatMap(structuredFormatting ->
                    retrofitWeather.create(WeatherAPIService.class)
                            .getWeather(structuredFormatting.getMainText()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::cityWeatherResult, this::cityWeatherError);
    }


    private void cityWeatherResult(OpenWeatherResponse openWeatherResponse) {
        if (cityName.getText().toString().isEmpty()){
            cityWeatherList.clear();
        } else {
            cityWeatherList.add(new CityWeather(openWeatherResponse.getName(),
                    kalvinToCelsius(openWeatherResponse.getMain().getTemp())));
        }
        cityWeatherAdapter.updateList(cityWeatherList);

        Log.d(TAG, "cityWeatherResult: " + openWeatherResponse.getName() + " "
                + kalvinToCelsius(openWeatherResponse.getMain().getTemp()));
    }



    private void cityWeatherError(Throwable throwable) {
        Log.d(TAG, "cityWeatherError: " + throwable.getMessage());
    }



    private String kalvinToCelsius(Float tempInKalvin){
        return String.valueOf(Math.round(tempInKalvin - 273.15F));
    }

}
