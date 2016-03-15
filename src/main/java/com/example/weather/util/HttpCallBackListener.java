package com.example.weather.util;

/**
 * Created by Gypsophila on 2016/3/13.
 */
public interface HttpCallBackListener {

    void onFinish(String response);

    void onError(Exception e);
}
