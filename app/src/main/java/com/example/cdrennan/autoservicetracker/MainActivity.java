package com.example.cdrennan.autoservicetracker;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;

//http://www.gadgetsaint.com/android/create-viewpager-tabs-android/#.W--RIEgvyUk
//https://developer.android.com/training/implementing-navigation/lateral#java
//https://www.simplifiedcoding.net/android-tablayout-example-using-viewpager-fragments/
public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.pager);
        Pager adapter = new Pager(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tabs_fragment);
        tabLayout.setupWithViewPager(viewPager);

//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            tab.setCustomView(adapter.getTabView(i));
//        }

//        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                tabLayout.setScrollPosition(position, 0, true);
//                tabLayout.setSelected(true);
//            }
//        });
//        tabLayout.addOnTabSelectedListener(this);
        tabLayout.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab){

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }
                });
    }
}
