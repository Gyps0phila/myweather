package com.example.weather.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weather.db.WeatherDB;
import com.example.weather.model.City;
import com.example.weather.model.WeatherInfo;
import com.example.weather.myweather.R;
import com.example.weather.util.HttpCallBackListener;
import com.example.weather.util.HttpUtil;
import com.example.weather.util.Utility;

import java.util.List;

/**
 * Created by Gypsophila on 2016/3/13.
 */
public class BootAty extends Activity {


    private Button searchCity,btn_back;
    private EditText cityName;
    private WeatherDB weatherDB;

    private boolean flag = true;

    public static void startAction(Context context, boolean flag) {
        Intent intent = new Intent(context, BootAty.class);
        intent.putExtra("flag", flag);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如果有储存的城市，则直接跳转
        //同时考虑已有城市，但要执行添加而跳转到这个页面
        flag = getIntent().getBooleanExtra("flag", true);
        weatherDB = WeatherDB.getInstance(BootAty.this,1);

        final List<City> cities = weatherDB.loadCities();

        if (cities.size() != 0 && flag == true) {

            //跳转到主页面
            startActivity(new Intent(this, MainAty.class));
            finish();
            return;
        }
        setContentView(R.layout.boot);
        searchCity = (Button) findViewById(R.id.search_city);
        btn_back = (Button) findViewById(R.id.btn_back);
        cityName = (EditText) findViewById(R.id.city_name);
        weatherDB = WeatherDB.getInstance(this,1);

        searchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(cityName.getText().toString())) {

                    //进行网络请求，并存入数据库
                    //无法判断输入的城市是否合法，根据解析返回数据进行判断
                    String name = cityName.getText().toString();
                    HttpUtil.sendHttpRequest(name, new HttpCallBackListener() {
                        @Override
                        public void onFinish(String response) {
                            WeatherInfo info = Utility.parseWeatherJson(response);
                            if (info != null) {
                                //不为空，存在该城市，还要判断是否已添加过了
                                if (weatherDB.contains(info.getCityName()) != null) {
                                    //存在该城市，就要更新数据
                                    weatherDB.updateWeatherInfo(info);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(BootAty.this, "已更新该城市", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                } else {
                                    long id = weatherDB.saveCity(new City(info.getCityName()));
                                    weatherDB.addWeatherInfo(info, id);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(BootAty.this, "已添加该城市", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(BootAty.this, "无法查询到该城市", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }

                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });

                }
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果数据库储存的城市数据为空，则返回就直接退出桌面，而不是进入主页面
                WeatherDB weatherDB = WeatherDB.getInstance(BootAty.this,1);
                List<City> cities = weatherDB.loadCities();
                if (cities.size() == 0) {
                    ActivityCollector.finishAll();

                } else {
                    ActivityCollector.finishAll();
                    startActivity(new Intent(BootAty.this, MainAty.class));
                }
                finish();
//                Toast.makeText(BootAty.this, "click" + cities.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void addCityName(String cityName) {
        //拼接请求链接
        //添加关注的城市加入数据库

        if (weatherDB.contains(cityName) != null) {
            Toast.makeText(this, "已添加该城市", Toast.LENGTH_SHORT).show();
            return;
        } else {

            final long cityId = weatherDB.saveCity(new City(cityName));
            HttpUtil.sendHttpRequest(cityName, new HttpCallBackListener() {
                @Override
                public void onFinish(String response) {
                    //请求返回时候的回调处理
                    //将刚刚添加的城市相关的天气数据保存在数据库
                    WeatherInfo weatherInfo = Utility.parseWeatherJson(response);
                    weatherDB.addWeatherInfo(weatherInfo, cityId);

//                    Bundle bundle = new Bundle();

                }

                @Override
                public void onError(Exception e) {

                }
            });
        }
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
                cityName = "深圳";
                break;
            }
            case R.id.fourth: {
                cityName = "广州";
                break;
            }
            case R.id.fifth: {
                cityName = "重庆";
                break;
            }

            case R.id.sixth: {
                cityName = "福州";
                break;
            }
            case R.id.seventh: {
                cityName = "天津";
                break;
            }
            case R.id.eighth: {
                cityName = "西安";
                break;
            }
            case R.id.nineth: {
                cityName = "泉州";
                break;
            }
            case R.id.tenth: {
                cityName = "福清";
                break;
            }
            case R.id.eleventh: {
                cityName = "香港";
                break;
            }
            case R.id.twelfth: {
                cityName = "澳门";
                break;
            }
        }

        addCityName(cityName);
        }


}
