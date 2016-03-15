package com.example.gypsophila.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gypsophila.adapter.WeatherFragmentPagerAdapter;
import com.example.gypsophila.db.WeatherDB;
import com.example.gypsophila.model.WeatherInfo;
import com.example.gypsophila.myweather.R;

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
        weatherDB = WeatherDB.getInstance(this);
        weatherInfos = weatherDB.loadWeatherInfos();
        for (int i = 0; i < weatherInfos.size(); i++) {
            WeatherInfoFragment fragment = new WeatherInfoFragment();
//            fragment.setData(weatherDB.loadWeatherInfos().get(i));
            WeatherInfo weatherInfo = weatherInfos.get(i);
            Bundle bundle = new Bundle();
            bundle.putString("city_name", weatherInfo.getCityName());
            bundle.putString("minTemp", weatherInfo.getMinTemp());
            bundle.putString("maxTemp", weatherInfo.getMaxTemp());
            bundle.putString("currentTemp", weatherInfo.getCurrentTemp());
            bundle.putString("date", weatherInfo.getDate());
            bundle.putString("weather", weatherInfo.getWeather());
            bundle.putString("loc", weatherInfo.getLoc());
            fragment.setArguments(bundle);

            Log.i("city_name", weatherInfo.getCityName());


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
