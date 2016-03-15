package com.example.weather.model;

/**
 * Created by Gypsophila on 2016/3/13.
 */
public class City {

    private String cityName;

    public City() {

    }

    public City(String cityName) {
        this.cityName = cityName;
    }
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
