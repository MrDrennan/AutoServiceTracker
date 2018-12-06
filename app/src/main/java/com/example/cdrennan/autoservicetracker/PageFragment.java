package com.example.cdrennan.autoservicetracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PageFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int tabNum;



    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabNum = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int fragment;
        switch (tabNum){
            case 0:
                fragment = R.layout.vehicles_tab;
                break;
            case 1:
                fragment = R.layout.service_tab;
                break;
            case 2:
                fragment = R.layout.history_tab;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return inflater.inflate(fragment, container, false);
    }
}
