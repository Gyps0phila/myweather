package com.example.gypsophila.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gypsophila.model.WeatherInfo;
import com.example.gypsophila.myweather.R;


/**
 * Created by Gypsophila on 2016/3/14.
 */
public class WeatherInfoFragment extends Fragment {


    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.weather_info_frag, container, false);
        Bundle bundle = getArguments();

        TextView city_name = (TextView) view.findViewById(R.id.city_name);
        TextView info_loc = (TextView) view.findViewById(R.id.info_loc);
        TextView current_temp = (TextView) view.findViewById(R.id.current_temp);
        TextView current_date = (TextView) view.findViewById(R.id.current_date);
        TextView weather_desp = (TextView) view.findViewById(R.id.weather_desp);
        TextView temp1 = (TextView) view.findViewById(R.id.temp1);
        TextView temp2 = (TextView) view.findViewById(R.id.temp2);
        city_name.setText(bundle.getString("city_name"));

        info_loc.setText(bundle.getString("loc"));
        current_temp.setText(bundle.getString("currentTemp"));
        current_date.setText(bundle.getString("date"));
        weather_desp.setText(bundle.getString("weather"));
        temp1.setText(bundle.getString("minTemp"));
        temp2.setText(bundle.getString("maxTemp"));

        return view;

    }

}
