package com.example.weather.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.weather.adapter.WeatherFragmentPagerAdapter;
import com.example.weather.db.WeatherDB;
import com.example.weather.model.WeatherInfo;
import com.example.weather.myweather.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gypsophila on 2016/3/13.
 */
public class MainAty extends FragmentActivity {



    private TextView cityName;
    private TextView infoLoc;
    private TextView currentTemp;
    private TextView date;
    private TextView weatherDesc;
    private TextView minTemp;
    private TextView maxTemp;
    private ViewPager pager;
    private List<WeatherInfoFragment> fragList;
    private WeatherDB weatherDB;
    private List<WeatherInfo> weatherInfos;
    private Button home;
    private Button refresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        pager = (ViewPager) findViewById(R.id.pager);
        fragList = new ArrayList<WeatherInfoFragment>();
        weatherDB = WeatherDB.getInstance(this,1);
        weatherInfos = weatherDB.loadWeatherInfos();
        for (int i = 0; i < weatherInfos.size(); i++) {
            WeatherInfoFragment fragment = new WeatherInfoFragment();
//            fragment.setData(weatherDB.loadWeatherInfos().get(i));
            WeatherInfo weatherInfo = weatherInfos.get(i);

            Log.i("weatherInfo", "db:weather=" + weatherInfo.getWeather());
            Log.i("weatherInfo", "db:weather=" + weatherInfo.toString());

            Bundle bundle = new Bundle();
            bundle.putString("city_name", weatherInfo.getCityName());
            bundle.putString("minTemp", weatherInfo.getMinTemp());
            bundle.putString("maxTemp", weatherInfo.getMaxTemp());
            bundle.putString("currentTemp", weatherInfo.getCurrentTemp());
            bundle.putString("date", weatherInfo.getDate());
            bundle.putString("weather", weatherInfo.getWeather());
            bundle.putString("loc", weatherInfo.getLoc());
            fragment.setArguments(bundle);


            fragList.add(fragment);
        }
        WeatherFragmentPagerAdapter adapter = new WeatherFragmentPagerAdapter(getSupportFragmentManager(), fragList);
        pager.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCollector.finishAll();
        finish();
    }

}
