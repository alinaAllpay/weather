package kz.myhome.weather.model;


/**
 * Created by Alina.Sibiryakova on 01.05.2018.
 */

public class CityWeather {
    private String cityName;
    private String temperature;

    public CityWeather(String cityName, String temperature) {
        this.cityName = cityName;
        this.temperature = temperature;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
