package com.example.gypsophila.myweather;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.gypsophila.db.WeatherDB;
import com.example.gypsophila.model.City;
import com.example.gypsophila.model.WeatherInfo;
import com.example.gypsophila.util.HttpCallBackListener;
import com.example.gypsophila.util.HttpUtil;
import com.example.gypsophila.util.Utility;

/**
 * Created by Gypsophila on 2016/3/13.
 */
public class DBTest extends AndroidTestCase {


    public void testAddCity() {
        City city = new City("福州");
        WeatherDB weatherDB = WeatherDB.getInstance(getContext());
        weatherDB.saveCity(city);

        assertEquals(2, weatherDB.loadCities().size());
    }

    public void testRemoveCity() {
        City city = new City("福州");
        WeatherDB weatherDB = WeatherDB.getInstance(getContext());
        weatherDB.removeCity(city);
        assertEquals(1, weatherDB.loadCities().size());
    }

    public void testParseJson() {
        String cityName = "福州";
        String address = Utility.URLPREFIX + cityName + Utility.URLEND;
        HttpUtil.sendHttpRequest(address, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                WeatherInfo weatherInfo = Utility.parseWeatherJson(response);
//                Log.i("weatherInfo", weatherInfo.toString());
//                System.out.println(weatherInfo.toString());
                //测试无论输入什么地名都被通过了

                assertEquals("北京", weatherInfo.getCityName());
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
