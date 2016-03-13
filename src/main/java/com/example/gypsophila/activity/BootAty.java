package com.example.gypsophila.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gypsophila.db.WeatherDB;
import com.example.gypsophila.model.City;
import com.example.gypsophila.model.WeatherInfo;
import com.example.gypsophila.myweather.R;
import com.example.gypsophila.util.HttpCallBackListener;
import com.example.gypsophila.util.HttpUtil;
import com.example.gypsophila.util.Utility;

/**
 * Created by Gypsophila on 2016/3/13.
 */
public class BootAty extends Activity {


    private Button searchCity;
    private EditText cityName;
    private WeatherDB weatherDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boot);
        searchCity = (Button) findViewById(R.id.search_city);
        cityName = (EditText) findViewById(R.id.city_name);
        weatherDB = WeatherDB.getInstance(this);

        searchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void doClick(View view) {

        String urlString = null;
        String cityName = null;
        switch (view.getId()) {
            case R.id.first: {
                cityName = "北京";
                break;
            }
            case R.id.second: {
                cityName = "上海";
                break;
            }
            case R.id.third: {
                cityName = "广州";
                break;
            }
        }
        //拼接请求链接
        urlString = Utility.URLPREFIX + cityName + Utility.URLEND;
        //添加关注的城市加入数据库
        weatherDB.saveCity(new City(cityName));

        HttpUtil.sendHttpRequest(urlString, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                //请求返回时候的回调处理
                WeatherInfo weatherInfo = Utility.parseWeatherJson(response);
                Log.i("weatherInfo", weatherInfo.toString());
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
