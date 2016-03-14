package com.example.gypsophila.activity;

import android.app.Activity;
import android.content.Intent;
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

import java.util.List;

/**
 * Created by Gypsophila on 2016/3/13.
 */
public class BootAty extends Activity {


    private Button searchCity,btn_back;
    private EditText cityName;
    private WeatherDB weatherDB;

    public static void startAction() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如果有储存的城市，则直接跳转
        weatherDB = WeatherDB.getInstance(BootAty.this);
        List<City> cities = weatherDB.loadCities();
        if (cities.size() != 0) {
            //跳转到主页面
            startActivity(new Intent(this, MainAty.class));
            finish();
            return;
        }
        setContentView(R.layout.boot);
        searchCity = (Button) findViewById(R.id.search_city);
        btn_back = (Button) findViewById(R.id.btn_back);
        cityName = (EditText) findViewById(R.id.city_name);
        weatherDB = WeatherDB.getInstance(this);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果数据库储存的城市数据为空，则返回就直接退出桌面，而不是进入主页面
                WeatherDB weatherDB = WeatherDB.getInstance(BootAty.this);
                List<City> cities = weatherDB.loadCities();
                if (cities.size()==0) {
                    ActivityCollector.finishAll();
                    finish();
                } else {
                    startActivity(new Intent(BootAty.this,MainAty.class));
                }
                Toast.makeText(BootAty.this, "click"+cities.toString(), Toast.LENGTH_SHORT).show();
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
        if (weatherDB.contains(cityName) != null) {
            Toast.makeText(this, "已添加该城市", Toast.LENGTH_SHORT).show();
            return;
        } else {
            final long cityId = weatherDB.saveCity(new City(cityName));
            HttpUtil.sendHttpRequest(urlString, new HttpCallBackListener() {
                @Override
                public void onFinish(String response) {
                    //请求返回时候的回调处理
                    //将刚刚添加的城市相关的天气数据保存在数据库
                    WeatherInfo weatherInfo = Utility.parseWeatherJson(response);
                    weatherDB.addWeatherInfo(weatherInfo, cityId);

//                    Bundle bundle = new Bundle();

                    Log.i("weatherInfo", weatherInfo.toString());
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }

    }
}
