package com.example.weather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gypsophila on 2016/3/13.
 */
public class WeatherOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_CITY = "create table city (_id integer primary key autoincrement, city_name text not null)";

    public static final String CREATE_WEATHER_INFO = "create table weather_info ("
            + "_id integer primary key autoincrement, "
            + "city_name text not null, "
            + "min_temp text not null, "
            + "max_temp text not null, "
            + "current_temp text not null, "
            + "date text not null, "
            + "weather text not null, "
            + "loc text not null, "
            + "city_id integer not null)";



    public WeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_WEATHER_INFO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (newVersion) {
            case 1:
                db.execSQL(CREATE_CITY);
            case 2:
                db.execSQL(CREATE_WEATHER_INFO);
            default:
        }
    }
}
