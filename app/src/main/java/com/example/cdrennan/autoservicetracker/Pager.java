package com.example.cdrennan.autoservicetracker;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;

public class Pager extends FragmentPagerAdapter {
    private Context context;
    final int TAB_COUNT = 3;

    public Pager(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

//    public View getTabView(int position) {
//        return LayoutInflater.from(context).inflate(tab, null);
//    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position);
//        switch (position) {
//            case 0:
//                return new VehiclesTab();
//            case 1:
//                return new ServiceTab();
//            case 2:
//                return new HistoryTab();
//            default:
//                throw new IllegalArgumentException();
//        }
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch (position) {
            case 0:
                return "VEHICLES";
            case 1:
                return "SERVICE";
            case 2:
                return "HISTORY";
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
