package com.example.cdrennan.autoservicetracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Locale;

public class VehiclesTab extends Fragment{

    private EditText editTextVehicleName;
    private EditText editTextMake;
    private EditText editTextModel;
    private EditText editTextYear;
    private EditText editTextMileage;
    private EditText editTextEngine;

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
        Vehicle vehicle = db.getVehicle(1);
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
