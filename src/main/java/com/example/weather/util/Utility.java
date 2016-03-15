package com.example.weather.util;

import com.example.weather.model.WeatherInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gypsophila on 2016/3/13.
 */
public class Utility {



    public static WeatherInfo parseWeatherJson(String jsonString) {
        WeatherInfo info = new WeatherInfo();

        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray array = object.getJSONArray("HeWeather data service 3.0");
            //这边是针对具体城市，虽然是数组，但是只有一个元素
            JSONObject weather = array.getJSONObject(0);
            JSONObject now = weather.getJSONObject("now");
            //设置当前温度
            info.setCurrentTemp(now.getString("tmp"));
            //设置当前描述
            JSONObject cond = now.getJSONObject("cond");
            info.setWeather(cond.getString("txt"));
            //取出最近几日的天气预测的第一天
            JSONArray dailyForecast = weather.getJSONArray("daily_forecast");
            JSONObject today = dailyForecast.getJSONObject(0);
            //设置今日日期
            info.setDate(today.getString("date"));
            JSONObject tmp = today.getJSONObject("tmp");
            info.setMinTemp(tmp.getString("min"));
            info.setMaxTemp(tmp.getString("max"));
            //取得数据更新的当地时间
            JSONObject basic = weather.getJSONObject("basic");
            JSONObject update = basic.getJSONObject("update");
            info.setLoc(update.getString("loc"));
            info.setCityName(basic.getString("city"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return info;
    }

}
