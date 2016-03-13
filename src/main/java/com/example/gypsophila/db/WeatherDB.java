package com.example.gypsophila.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gypsophila.model.City;

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
        WeatherOpenHelper dbHelper = new WeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public synchronized static WeatherDB getInstance(Context context) {
        if (weatherDB == null) {
            weatherDB = new WeatherDB(context);
        }
        return weatherDB;
    }

    public void saveCity(City city) {
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            db.insert("city", null, values);
        }
    }

    public List<City> loadCities() {
        List<City> cities = new ArrayList<City>();
        Cursor cursor = db.query("city", null, null, null, null, null, null);
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


}
