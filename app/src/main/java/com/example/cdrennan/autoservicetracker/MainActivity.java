package com.example.cdrennan.autoservicetracker;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//http://www.gadgetsaint.com/android/create-viewpager-tabs-android/#.W--RIEgvyUk
//https://developer.android.com/training/implementing-navigation/lateral#java
//https://www.simplifiedcoding.net/android-tablayout-example-using-viewpager-fragments/
public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("VEHICLES"));
        tabLayout.addTab(tabLayout.newTab().setText("SERVICE"));
        tabLayout.addTab(tabLayout.newTab().setText("HISTORY"));

        viewPager = findViewById(R.id.pager);

        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.setScrollPosition(position, 0, true);
                tabLayout.setSelected(true);
            }
        });
        tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
