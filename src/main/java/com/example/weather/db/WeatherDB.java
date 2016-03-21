package com.example.weather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.weather.model.City;
import com.example.weather.model.WeatherInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gypsophila on 2016/3/13.
 */
public class WeatherDB {

    public static final String DB_NAME = "weather.db";

    public static final int VERSION = 1;
    private static WeatherDB weatherDB;

    private SQLiteDatabase db;

    private WeatherDB(Context context) {
        this(context, VERSION);
    }

    private WeatherDB(Context context, int version) {
        WeatherOpenHelper dbHelper = new WeatherOpenHelper(context, DB_NAME, null, version);
        db = dbHelper.getWritableDatabase();
    }

    public synchronized static WeatherDB getInstance(Context context,int version) {
        if (weatherDB == null) {
            weatherDB = new WeatherDB(context,version);
        }
        return weatherDB;
    }

    public long saveCity(City city) {
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            long city_id = db.insert("city", null, values);
            return city_id;
        }
        return -1;
    }

    public List<City> loadCities() {
        List<City> cities = new ArrayList<City>();
        Cursor cursor = db.query("city", null, null, null, null, null, "_id desc");
        if (cursor.moveToFirst()) {

            do {
                City city = new City();
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                cities.add(city);
            } while (cursor.moveToNext());
        }
        return cities;
    }

    public boolean removeCity(City city) {

        if (city != null) {
            db.delete("city", "city_name = ?", new String[]{city.getCityName()});
            return true;
        }
        return false;
    }

    public City contains(String cityName) {
        if (!cityName.equals("")) {
            Cursor cs = db.query("city", null, "city_name = ?", new String[]{cityName}, null, null, null);
            if (cs.moveToFirst()) {
                City city = new City(cs.getString(cs.getColumnIndex("city_name")));
                return city;
            }
        }
        return null;
    }

    //将最近的天气是存入

    public void addWeatherInfo(WeatherInfo info,long cityId) {
        if (info != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", info.getCityName());
            values.put("min_temp", info.getMinTemp());
            values.put("max_temp", info.getMaxTemp());
            values.put("current_temp", info.getCurrentTemp());
            values.put("weather", info.getWeather());
            values.put("loc", info.getLoc());
            values.put("date", info.getDate());
            values.put("city_id", cityId);
            db.insert("weather_info", null, values);
        }
    }

    public List<WeatherInfo> loadWeatherInfos() {
        List<WeatherInfo> weatherInfos = new ArrayList<WeatherInfo>();
        Cursor cs = db.query("weather_info", null, null, null, null, null, "_id desc");
        if (cs.moveToFirst()) {
            do {
                WeatherInfo weatherInfo = new WeatherInfo();
                weatherInfo.setCityName(cs.getString(cs.getColumnIndex("city_name")));
                weatherInfo.setCurrentTemp(cs.getString(cs.getColumnIndex("current_temp")));
                weatherInfo.setMinTemp(cs.getString(cs.getColumnIndex("min_temp")));
                weatherInfo.setMaxTemp(cs.getString(cs.getColumnIndex("max_temp")));
                weatherInfo.setLoc(cs.getString(cs.getColumnIndex("loc")));
                weatherInfo.setDate(cs.getString(cs.getColumnIndex("date")));
                weatherInfo.setWeather(cs.getString(cs.getColumnIndex("weather")));
                weatherInfos.add(weatherInfo);
            } while (cs.moveToNext());
        }
        return weatherInfos;
    }

    public int updateWeatherInfo(WeatherInfo weatherInfo) {

        ContentValues values = new ContentValues();
        values.put("min_temp", weatherInfo.getMinTemp());
        values.put("max_temp", weatherInfo.getMaxTemp());
        values.put("current_temp", weatherInfo.getCurrentTemp());
        values.put("weather", weatherInfo.getWeather());
        values.put("loc", weatherInfo.getLoc());
        values.put("date", weatherInfo.getDate());
        int updateRow = db.update("weather_info", values, "city_name= ?", new String[]{weatherInfo.getCityName()});
        return updateRow;
    }


}
