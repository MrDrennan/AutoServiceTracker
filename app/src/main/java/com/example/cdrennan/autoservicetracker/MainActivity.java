package com.example.cdrennan.autoservicetracker;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;

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
