package com.example.gypsophila.activity;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Gypsophila on 2016/3/13.
 */
public class BaseAty extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
