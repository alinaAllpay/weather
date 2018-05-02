package kz.myhome.weather.service;

import io.reactivex.Observable;
import kz.myhome.weather.model.place.GooglePlacesResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Alina.Sibiryakova on 29.04.2018.
 */

public interface PlaceAPIService {
        String SERVICE_ENDPOINT = "https://maps.googleapis.com";

        @GET("/maps/api/place/autocomplete/json?components=country:kz&types=(cities)&key=AIzaSyA4gnz9niFy-7zEVsdDYylzswDjh4ovKok")
        Observable<GooglePlacesResponse> getCities(@Query("input") String input);
}
