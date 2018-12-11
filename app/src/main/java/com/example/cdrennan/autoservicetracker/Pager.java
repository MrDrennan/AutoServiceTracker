package com.example.cdrennan.autoservicetracker;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class Pager extends FragmentPagerAdapter {
    private Context context;
    private final int TAB_COUNT = 3;
    private VehiclesTab vehiclesTab;
    private ServiceTab serviceTab;
    private HistoryTab historyTab;
    public static final String ARG_VEHICLE_ID = "VEHICLE_ID";
    public static final String ARG_SERVICE_ID = "SERVICE_ID";
    public static final String[] ID_ARGS = {"VEHICLE_ID", "SERVICE_ID"};

    public Pager(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public Fragment getFragment(int position){
        switch(position){
            case 0:
                return vehiclesTab;
            case 1:
                return serviceTab;
            case 2:
                return historyTab;
            default:
                throw new IllegalArgumentException();
        }
    }

    public VehiclesTab getVehiclesTab(){
        return vehiclesTab;
    }

    public ServiceTab getServiceTab(){
        return serviceTab;
    }

    public HistoryTab getHistoryTab(){
        return historyTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                vehiclesTab = VehiclesTab.newInstance();
                return vehiclesTab;
            case 1:
                serviceTab = ServiceTab.newInstance();
                return serviceTab;
            case 2:
                historyTab = HistoryTab.newInstance();
                return  historyTab;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch (position) {
            case 0:
                return "VEHICLES";
            case 1:
                return "SERVICES";
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
