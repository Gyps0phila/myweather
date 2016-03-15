package com.example.weather.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.db.WeatherDB;
import com.example.weather.model.City;
import com.example.weather.model.WeatherInfo;
import com.example.weather.myweather.R;
import com.example.weather.util.HttpCallBackListener;
import com.example.weather.util.HttpUtil;
import com.example.weather.util.Utility;


/**
 * Created by Gypsophila on 2016/3/14.
 */
public class WeatherInfoFragment extends Fragment implements View.OnClickListener{


    private View view;
    private Button home;
    private Button refresh;
    private TextView city_name;
    private TextView info_loc;
    private TextView current_temp;
    private TextView current_date;
    private TextView weather_desp;
    private TextView temp1;
    private TextView temp2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.weather_info_frag, container, false);
        home = (Button) view.findViewById(R.id.home);
        refresh = (Button) view.findViewById(R.id.refresh_weather);
        home.setOnClickListener(this);
        Bundle bundle = getArguments();

        city_name = (TextView) view.findViewById(R.id.city_name);
        info_loc = (TextView) view.findViewById(R.id.info_loc);
        current_temp = (TextView) view.findViewById(R.id.current_temp);
        current_date = (TextView) view.findViewById(R.id.current_date);
        weather_desp = (TextView) view.findViewById(R.id.weather_desp);
        temp1 = (TextView) view.findViewById(R.id.temp1);
        temp2 = (TextView) view.findViewById(R.id.temp2);


        city_name.setText(bundle.getString("city_name"));
        info_loc.setText(bundle.getString("loc"));
        current_temp.setText(bundle.getString("currentTemp"));
        current_date.setText(bundle.getString("date"));
        weather_desp.setText(bundle.getString("weather"));
        temp1.setText(bundle.getString("minTemp"));
        temp2.setText(bundle.getString("maxTemp"));

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home: {
                BootAty.startAction(getActivity(), false);
                getActivity().finish();
                break;
            }
            case R.id.refresh_weather: {
                String cityName = city_name.getText().toString();
                //重新去网络获取最新
                Toast.makeText(getActivity(), "更新完成", Toast.LENGTH_SHORT).show();

                HttpUtil.sendHttpRequest(cityName, new HttpCallBackListener() {
                    @Override
                    public void onFinish(String response) {
                        WeatherInfo info = Utility.parseWeatherJson(response);
                        WeatherDB weatherDB = WeatherDB.getInstance(getActivity(),1);
                        City city = new City(info.getCityName());
                        weatherDB.removeCity(city);
                        long cityId = weatherDB.saveCity(city);
                        weatherDB.addWeatherInfo(info, cityId);
                        city_name.setText(info.getCityName());
                        info_loc.setText(info.getLoc());
                        current_temp.setText(info.getCurrentTemp());
                        current_date.setText(info.getDate());
                        weather_desp.setText(info.getWeather());
                        temp1.setText(info.getMinTemp());
                        temp2.setText(info.getMaxTemp());
                        Toast.makeText(getActivity(), "更新完成", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
                break;
            }
        }
    }
}
