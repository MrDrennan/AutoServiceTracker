package com.example.cdrennan.autoservicetracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

public class Pager extends FragmentPagerAdapter {

    int tabCount;

    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new VehiclesTab();
            case 1:
                return new ServiceTab();
            case 2:
                return new HistoryTab();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
