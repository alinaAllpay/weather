package kz.myhome.weather;

/**
 * Created by Alina.Sibiryakova on 01.05.2018.
 */


import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kz.myhome.weather.model.CityWeather;

class CityWeatherAdapter extends RecyclerView.Adapter<CityWeatherAdapter.ViewHolder> {

    private List<CityWeather> cityWeatherList = new ArrayList<>();

    public CityWeatherAdapter(List<CityWeather> cityWeatherList) {
        this.cityWeatherList = cityWeatherList;
    }

    @Override
    public CityWeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_weather_item, parent, false);

        CityWeatherAdapter.ViewHolder viewHolder = new CityWeatherAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CityWeatherAdapter.ViewHolder holder, int position) {
        CityWeather cityWeather = cityWeatherList.get(position);
        holder.cityName.setText(cityWeather.getCityName());
        holder.temperature.setText(cityWeather.getTemperature().toString());
    }

    @Override
    public int getItemCount() {
        return cityWeatherList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView cityName;
        public TextView temperature;

        public ViewHolder(View view) {
            super(view);

            cityName =  view.findViewById(R.id.city_name);
            temperature =  view.findViewById(R.id.temperature);
        }

    }

    public void updateList(List<CityWeather> newCityWeatherList) {
        final CitiesDiffCallback diffCallback = new CitiesDiffCallback(newCityWeatherList, this.cityWeatherList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback, false);
        this.cityWeatherList.clear();
        this.cityWeatherList.addAll(newCityWeatherList);
        diffResult.dispatchUpdatesTo(CityWeatherAdapter.this);


    }

    private List<CityWeather> getSortedList(List<CityWeather> cityWeatherList) {
        Collections.sort(cityWeatherList, (t1, t2) -> t1.getCityName().compareTo(t2.getCityName()));
        return cityWeatherList;
    }
}