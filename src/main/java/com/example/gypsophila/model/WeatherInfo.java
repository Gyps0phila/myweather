package com.example.gypsophila.model;

/**
 * Created by Gypsophila on 2016/3/13.
 */
public class WeatherInfo {

    private String cityName;
    //最低温度 对应api daily_forecast/tmp/min
    private String minTemp;
    //当前温度对应 api now/temp
    private String currentTemp;
    //最高温度  daily_forecast/tmp/max
    private String maxTemp;
    //天气描述对应api的 now/cond/txt 字段
    private String weather;
    //数据更新时间
    private String loc;

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(String currentTemp) {
        this.currentTemp = currentTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String utc) {
        this.loc = utc;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "cityName='" + cityName + '\'' +
                ", minTemp='" + minTemp + '\'' +
                ", currentTemp='" + currentTemp + '\'' +
                ", maxTemp='" + maxTemp + '\'' +
                ", weather='" + weather + '\'' +
                ", loc='" + loc + '\'' +
                '}';
    }
}
