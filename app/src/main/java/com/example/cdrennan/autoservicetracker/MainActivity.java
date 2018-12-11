package com.example.cdrennan.autoservicetracker;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Pager adapter;
    private long vehicleId;
    private long serviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.pager);
        adapter = new Pager(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tabs_fragment);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);

                        //https://developer.android.com/training/basics/fragments/communicating
                        //https://stackoverflow.com/questions/37943474/pass-data-from-one-design-tab-to-another-tab
                        int position = tab.getPosition();
                        Bundle bundle = new Bundle();

                        switch (position){
                            case 1:
                                ServiceTab serviceTab = adapter.getServiceTab();
                                bundle.putLong(adapter.ARG_VEHICLE_ID, vehicleId);
                                serviceTab.setArguments(bundle);
                                serviceTab.loadData(vehicleId);
                                break;
                            case 2:
                                HistoryTab historyTab = adapter.getHistoryTab();
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
    }
}
