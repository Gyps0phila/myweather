package com.example.weather.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.weather.db.WeatherOpenHelper;

/**
 * Created by Gypsophila on 2016/3/15.
 */
public class WeatherProvider extends ContentProvider {

    public static final int WEATHER_DIR = 0;
    public static final int WEATHER_ITEM = 1;

    public static final String AUTHORITY = "com.example.weather.myweather.provider";
    private static UriMatcher uriMatcher;
    private WeatherOpenHelper dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "weather_info", WEATHER_DIR);
        uriMatcher.addURI(AUTHORITY, "weather_info/#", WEATHER_ITEM);
    }
    @Override
    public boolean onCreate() {
        dbHelper = new WeatherOpenHelper(getContext(), "weather.db", null, 1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case WEATHER_DIR: {
                cursor = db.query("weather_info", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case WEATHER_ITEM: {
                //取得传入的uri中最后的id信息decoded path segments, each without a leading or trailing '/'
                String id = uri.getPathSegments().get(1);
                cursor = db.query("weather_info", projection, "_id = ?", new String[]{id}, null, null, sortOrder);

                break;
            }
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case WEATHER_DIR: {
                return "vnd.android.cursor.dir/vnd.com.example.gypsophila.myweather.provider.weather_info";
            }
            case WEATHER_ITEM: {
                return "vnd.android.cursor.item/vnd.com.example.gypsophila.myweather.provider.weather_info";
            }
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case WEATHER_ITEM:
            case WEATHER_DIR:
                    long newId = db.insert("weather_info", null, values);
                    //这个uri信息包含刚刚插入的id信息，返回可以使得其他程序拿到id，再进行另外操作
                    uriReturn = Uri.parse("content://" + AUTHORITY + "/weather_info" + newId);
                break;
            default:
                break;
        }
        return uriReturn;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deleteRows = 0;
        switch (uriMatcher.match(uri)) {
            case WEATHER_DIR: {
                deleteRows = db.delete("weather_info", selection, selectionArgs);
                break;
            }
            case WEATHER_ITEM: {
                String id = uri.getPathSegments().get(1);
                deleteRows = db.delete("weather_info", "_id = ?", new String[]{id});
                break;
            }
        }
        return deleteRows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updateRows = 0;
        switch (uriMatcher.match(uri)) {
            case WEATHER_DIR: {
                updateRows = db.update("weather_info", values, null, null);
                break;
            }
            case WEATHER_ITEM: {
                String id = uri.getPathSegments().get(1);
                updateRows = db.update("weather_info", values, "_id = ?", new String[]{id});
                break;
            }
            default:
                break;
        }
        return updateRows;
    }
}
