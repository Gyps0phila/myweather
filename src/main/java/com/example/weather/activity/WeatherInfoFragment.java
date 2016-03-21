package com.example.weather.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.db.WeatherDB;
import com.example.weather.model.City;
import com.example.weather.model.WeatherInfo;
import com.example.weather.myweather.R;
import com.example.weather.util.HttpCallBackListener;
import com.example.weather.util.HttpUtil;
import com.example.weather.util.Utility;


/**
 * Created by Gypsophila on 2016/3/14.
 */
public class WeatherInfoFragment extends Fragment implements View.OnClickListener{

    private View view;
    private Button home;
    private Button refresh;
    private TextView city_name;
    private TextView info_loc;
    private TextView current_temp;
    private TextView current_date;
    private TextView weather_desp;
    private TextView temp1;
    private TextView temp2;

    private static final int REFRESH = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH: {
                    WeatherInfo info = (WeatherInfo) msg.obj;
                    city_name.setText(info.getCityName());
                    info_loc.setText("当地更新:" + info.getLoc().substring(5));
                    current_temp.setText(info.getCurrentTemp());
                    current_date.setText(info.getDate());
                    weather_desp.setText(info.getWeather());
                    temp1.setText(info.getMinTemp());
                    temp2.setText(info.getMaxTemp());

                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.weather_info_frag, container, false);
        home = (Button) view.findViewById(R.id.home);
        refresh = (Button) view.findViewById(R.id.refresh_weather);
        home.setOnClickListener(this);
        refresh.setOnClickListener(this);

        Bundle bundle = getArguments();

        city_name = (TextView) view.findViewById(R.id.city_name);
        info_loc = (TextView) view.findViewById(R.id.info_loc);
        current_temp = (TextView) view.findViewById(R.id.current_temp);
        current_date = (TextView) view.findViewById(R.id.current_date);
        weather_desp = (TextView) view.findViewById(R.id.weather_desp);
        temp1 = (TextView) view.findViewById(R.id.temp1);
        temp2 = (TextView) view.findViewById(R.id.temp2);


        city_name.setText(bundle.getString("city_name"));
        info_loc.setText("当地更新:"+bundle.getString("loc").substring(5));
        current_temp.setText(bundle.getString("currentTemp"));
        current_date.setText(bundle.getString("date"));
        weather_desp.setText(bundle.getString("weather"));
        temp1.setText(bundle.getString("minTemp"));
        temp2.setText(bundle.getString("maxTemp"));

        Log.i("weatherInfo", "fragment:weather=" + bundle.getString("weather"));

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home: {
                //不是第一次进入
                BootAty.startAction(getActivity(), false);
                getActivity().finish();
                break;
            }
            case R.id.refresh_weather: {
                String cityName = city_name.getText().toString();
                Log.i("updateWeather", cityName);
                //重新去网络获取最新
                Toast.makeText(getActivity(), "开始更新", Toast.LENGTH_SHORT).show();

                HttpUtil.sendHttpRequest(cityName, new HttpCallBackListener() {
                    @Override
                    public void onFinish(String response) {
                        WeatherInfo info = Utility.parseWeatherJson(response);
                        WeatherDB weatherDB = WeatherDB.getInstance(getActivity(), 1);
                        Log.i("updateWeather", info.toString());
                        //根据名字去替换
                        int updateRow = weatherDB.updateWeatherInfo(info);
                        Message message = Message.obtain();
                        message.what = REFRESH;
                        message.obj = info;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

                Toast.makeText(getActivity(), "更新完成", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}
