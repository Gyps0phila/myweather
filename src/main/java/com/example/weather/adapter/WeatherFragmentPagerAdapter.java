package com.example.weather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.weather.activity.WeatherInfoFragment;

import java.util.List;

/**
 * Created by Gypsophila on 2016/3/14.
 */
public class WeatherFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<WeatherInfoFragment> fragList;

    public WeatherFragmentPagerAdapter(FragmentManager fm, List<WeatherInfoFragment> fragList) {
        super(fm);
        this.fragList = fragList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragList.get(position);
    }


    @Override
    public int getCount() {
        return fragList.size();
    }
}
