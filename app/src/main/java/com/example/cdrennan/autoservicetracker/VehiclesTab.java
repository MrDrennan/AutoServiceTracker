package com.example.cdrennan.autoservicetracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Locale;

public class VehiclesTab extends Fragment{

    private EditText editTextVehicleName;
    private EditText editTextMake;
    private EditText editTextModel;
    private EditText editTextYear;
    private EditText editTextMileage;
    private EditText editTextEngine;
    private RecyclerView vRecyclerView;
    private RecyclerView.LayoutManager vLayoutManager;
    private RecyclerView.Adapter RecyclerAdapter;

    public static VehiclesTab newInstance(){
        return new VehiclesTab();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vehicles_tab, container, false);

        editTextVehicleName = view.findViewById(R.id.editTextVehicleName);
        editTextMileage = view.findViewById(R.id.editTextMileage);
        editTextMake = view.findViewById(R.id.editTextMake);
        editTextModel = view.findViewById(R.id.editTextModel);
        editTextYear = view.findViewById(R.id.editTextYear);
        editTextEngine = view.findViewById(R.id.editTextEngine);
        

        

        Context context = getActivity().getApplicationContext();
        AutoServiceDb db = new AutoServiceDb(context);
        ArrayList<Vehicle> vehicles = db.getVehicles();
        String[] info = new String[vehicles.size()];
        int i = 0;
        for (Vehicle currVehicle: vehicles) {
            info[i] = currVehicle.getName() + " " + currVehicle.getMileage() + " miles";
            i++;
        }

        Vehicle vehicle = db.getVehicle(1);

        vRecyclerView = view.findViewById(R.id.recyclerViewVehicle);
        vRecyclerView.setHasFixedSize(true);


        vLayoutManager = new LinearLayoutManager(getActivity());
        vRecyclerView.setLayoutManager(vLayoutManager);

        //vLayoutManagerDividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
          //      vRecyclerView.getContext(), vLayoutManager.getOrientation())

        RecyclerAdapter = new RecyclerAdapter(info);
        vRecyclerView.setAdapter(RecyclerAdapter);

        fillForm(vehicle);

        return view;
    }

    private void fillForm(Vehicle vehicle){
        editTextVehicleName.setText(vehicle.getName());
        editTextMileage.setText(String.format(Locale.US, "%d", vehicle.getMileage()));
        editTextMake.setText(vehicle.getMake());
        editTextModel.setText(vehicle.getModel());
        editTextYear.setText(String.format(Locale.US, "%d",vehicle.getYear()));
        editTextEngine.setText(vehicle.getEngine());
    }
}
