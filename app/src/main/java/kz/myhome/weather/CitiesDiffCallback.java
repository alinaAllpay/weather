package kz.myhome.weather;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.util.Log;

import java.util.List;

import kz.myhome.weather.model.CityWeather;

/**
 * Created by Alina.Sibiryakova on 02.05.2018.
 */

class CitiesDiffCallback extends DiffUtil.Callback {
    private final List<CityWeather> newList;
    private final List<CityWeather> oldList;
    private static final String TAG = "DiffUtils";

    public CitiesDiffCallback(List<CityWeather> newCityWeatherList, List<CityWeather> cityWeatherList) {
        this.newList = newCityWeatherList;
        this.oldList = cityWeatherList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Log.d(TAG, "areItemsTheSame: " + oldList.get(oldItemPosition).getCityName() +
        " " + newList.get(newItemPosition).getCityName() + " " +
                oldList.get(oldItemPosition).getCityName().equals(newList.get(newItemPosition).getCityName()));

        return oldList.get(oldItemPosition).getCityName().equals(newList.get(newItemPosition).getCityName());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Log.d(TAG, "areContentsTheSame: " + oldList.get(oldItemPosition).getTemperature() +
        " " + newList.get(newItemPosition).getTemperature() + " " +
                (oldList.get(oldItemPosition).getTemperature().equals(newList.get(newItemPosition).getTemperature())));
        return oldList.get(oldItemPosition).getTemperature().equals(newList.get(newItemPosition).getTemperature());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
