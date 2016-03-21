package com.example.weather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.weather.service.AutoUpdateService;

/**
 * Created by Gypsophila on 2016/3/21.
 */
public class AutoUpdateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, AutoUpdateService.class);
        context.startService(i);
    }
}