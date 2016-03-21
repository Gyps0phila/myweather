package com.example.weather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.example.weather.db.WeatherDB;
import com.example.weather.model.WeatherInfo;
import com.example.weather.receiver.AutoUpdateReceiver;
import com.example.weather.util.HttpCallBackListener;
import com.example.weather.util.HttpUtil;
import com.example.weather.util.Utility;

import java.util.List;

/**
 * Created by Gypsophila on 2016/3/21.
 */
public class AutoUpdateService extends Service {

    WeatherDB weatherDB = WeatherDB.getInstance(this, 1);
    List<WeatherInfo> weatherInfos;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //不能在service中做耗时操作开线程去
            new Thread(new Runnable() {
                @Override
                public void run() {
                    updateWeather();
                }
            }).start();

        //计时器
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int hours = 4 * 60 * 60 * 1000;
//        int hours = 60 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + hours;
        Intent i = new Intent(this, AutoUpdateReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather() {
        weatherInfos = weatherDB.loadWeatherInfos();
        for (int i = 0; i < weatherInfos.size(); i++) {
            //更新数据库当前所有的天气信息
            HttpUtil.sendHttpRequest(weatherInfos.get(i).getCityName(), new HttpCallBackListener() {
                @Override
                public void onFinish(String response) {
                    WeatherInfo info = Utility.parseWeatherJson(response);
                    weatherDB.updateWeatherInfo(info);
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
