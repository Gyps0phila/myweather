package com.example.gypsophila.myweather;

import android.test.AndroidTestCase;

import com.example.gypsophila.db.WeatherDB;
import com.example.gypsophila.model.City;

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

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
