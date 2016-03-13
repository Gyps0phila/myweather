package com.example.gypsophila.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gypsophila.db.WeatherDB;
import com.example.gypsophila.model.City;
import com.example.gypsophila.myweather.R;
import com.example.gypsophila.util.HttpCallBackListener;
import com.example.gypsophila.util.HttpUtil;

/**
 * Created by Gypsophila on 2016/3/13.
 */
public class BootAty extends Activity {


    private Button search_city;
    private final String URLPREFIX = "https://api.heweather.com/x3/weather?city=";
    private final String URLEND = "&key=9939d20a58bf4b08bca00f9c56ff7217";
    private WeatherDB weatherDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boot);
        search_city = (Button) findViewById(R.id.search_city);
        weatherDB = WeatherDB.getInstance(this);
        search_city.setOnClickListener(new View.OnClickListener() {
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
        urlString = URLPREFIX + cityName + URLEND;
        //添加关注的城市加入数据库
        weatherDB.saveCity(new City(cityName));

        HttpUtil.sendHttpRequest(urlString, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                //请求返回时候的回调处理
                Log.i("response", response);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
