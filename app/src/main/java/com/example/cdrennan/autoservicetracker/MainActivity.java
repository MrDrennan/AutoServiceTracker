package com.example.cdrennan.autoservicetracker;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Pager adapter;
    private long vehicleId;
    private long serviceId;
    private boolean isReadyToAdd;
    private int currentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.pager);
        adapter = new Pager(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tabs_fragment);
        tabLayout.setupWithViewPager(viewPager);

        currentTab = 0;
        tabLayout.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);

                        //https://developer.android.com/training/basics/fragments/communicating
                        //https://stackoverflow.com/questions/37943474/pass-data-from-one-design-tab-to-another-tab
                        currentTab = tab.getPosition();
                        Bundle bundle = new Bundle();

                        switch (currentTab){
                            case 1:
                                ServiceTab serviceTab = adapter.getServiceTab();
                                if (serviceTab == null){
                                    return;
                                }
                                bundle.putLong(adapter.ARG_VEHICLE_ID, vehicleId);
                                serviceTab.setArguments(bundle);
                                serviceTab.loadData(vehicleId);
                                break;
                            case 2:
                                HistoryTab historyTab = adapter.getHistoryTab();
                                if (historyTab == null){
                                    return;
                                }
                                bundle.putLong(adapter.ARG_SERVICE_ID, serviceId);
                                historyTab.setArguments(bundle);
                                historyTab.loadData(serviceId);
                                break;
                        }
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab){

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        int position = tab.getPosition();
                        isReadyToAdd = false;

                        Fragment fragment = adapter.getFragment(position);
                        if (fragment == null){
                            return;
                        }

                        Bundle bundle = fragment.getArguments();
                        if (bundle != null){
                            long id;
                            switch (position){
                                case 0:
                                    vehicleId = bundle.getLong(adapter.ARG_VEHICLE_ID);
                                    break;
                                case 1:
                                    serviceId = bundle.getLong(adapter.ARG_SERVICE_ID);
                                    break;
                            }
                        }
                    }
                });

        FloatingActionButton fab = findViewById(R.id.fab);

        isReadyToAdd = false;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isReadyToAdd){
                    switch (currentTab){
                        case 0:
                            adapter.getVehiclesTab().addVehicle();
                            isReadyToAdd = false;
                            break;
                        case 1:
                            adapter.getServiceTab().addService(vehicleId);
                            isReadyToAdd = false;
                            break;
                        case 2:
                            adapter.getHistoryTab().addServiceLog(serviceId);
                            isReadyToAdd = false;
                            break;
                    }
                }
                else {
                    switch (currentTab){
                        case 0:
                            if (adapter.getVehiclesTab() == null){
                                return;
                            }
                            adapter.getVehiclesTab().clearForm();
                            isReadyToAdd = true;
                            break;
                        case 1:
                            adapter.getServiceTab().clearForm();
                            isReadyToAdd = true;
                            break;
                        case 2:
                            adapter.getHistoryTab().clearForm();
                            isReadyToAdd = true;
                            break;
                    }
                }
            }
        });
    }
}
