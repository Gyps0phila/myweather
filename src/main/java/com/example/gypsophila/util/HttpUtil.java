package com.example.gypsophila.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Gypsophila on 2016/3/13.
 */
public class HttpUtil {

    public static void sendHttpRequest(final String address, final HttpCallBackListener listener) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpsURLConnection conn = null;
                try {
                    URL url = new URL(address);
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
