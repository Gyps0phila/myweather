package com.example.weather.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Gypsophila on 2016/3/13.
 */
public class HttpUtil {

    public static final String URLPREFIX = "https://api.heweather.com/x3/weather?city=";

    public static final String URLEND = "&key=9939d20a58bf4b08bca00f9c56ff7217";

    public static void sendHttpRequest(final String cityName, final HttpCallBackListener listener) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpsURLConnection conn = null;
                try {
                    //url中带有中文，致使乱码错误！！！而模拟器上却可以
                    String address = URLPREFIX + URLEncoder.encode(cityName, "utf-8") + URLEND;
                    URL url = new URL(address);
                    Log.i("url", url.toString());
                    conn = (HttpsURLConnection) url.openConnection();
                    conn.setReadTimeout(8000);
                    conn.setRequestMethod("GET");

                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String str;
                    while ((str = br.readLine()) != null) {
                        sb.append(str);
                    }
                    br.close();
                    if (listener != null) {
                        listener.onFinish(sb.toString());
                    }
                } catch (IOException e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }

                }
            }
        }).start();
    }
}
