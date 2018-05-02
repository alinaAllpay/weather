package kz.myhome.weather.service;

import io.reactivex.Observable;
import kz.myhome.weather.model.weather.OpenWeatherResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Alina.Sibiryakova on 29.04.2018.
 */

public interface WeatherAPIService {
    String SERVICE_ENDPOINT = "https://api.openweathermap.org";

    @GET("/data/2.5/weather?APPID=dbf874b8dd179a539932b25cee8bed46")
    Observable<OpenWeatherResponse> getWeather(@Query("q") String city);
}
