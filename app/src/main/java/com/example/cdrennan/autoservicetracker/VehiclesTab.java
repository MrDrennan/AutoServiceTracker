package com.example.cdrennan.autoservicetracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
    private RecyclerView.Adapter recyclerAdapter;
    private ArrayList<Vehicle> vehicles;
    public static final String ARG_VEHICLE_ID = "VEHICLE_ID";
    private Context context;

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

        context = getActivity().getApplicationContext();
        AutoServiceDb db = new AutoServiceDb(context);
        vehicles = db.getVehicles();

        String[][] info = formatListData(vehicles);

        setupRecycler(context, view, info);

        fillForm(vehicles.get(0));
        return view;
    }

    private String[][] formatListData(ArrayList<Vehicle> vehicles){
        String[][] info = new String[vehicles.size()][2];
        for (int i = 0; i < vehicles.size(); i++){
            info[i][0] = vehicles.get(i).getName();
            info[i][1] = String.format(Locale.US, "%,d", vehicles.get(i).getMileage());
        }
        return info;
    }

    private void setupRecycler(final Context context, View view, String[][] listData){
        vRecyclerView = view.findViewById(R.id.recyclerViewVehicle);
        vRecyclerView.setHasFixedSize(true);


        vLayoutManager = new LinearLayoutManager(getActivity());
        vRecyclerView.setLayoutManager(vLayoutManager);

        recyclerAdapter = new RecyclerAdapter(listData);
        vRecyclerView.setAdapter(recyclerAdapter);

        DividerItemDecoration itemDecor = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecor.setDrawable(getContext().getResources().getDrawable(R.drawable.list_divider));
        vRecyclerView.addItemDecoration(itemDecor);

        vRecyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(context, vRecyclerView, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Vehicle selectedVehicle = vehicles.get(position);
                        fillForm(selectedVehicle);
                        Bundle args = new Bundle();
                        args.putLong(ARG_VEHICLE_ID, selectedVehicle.getId());
                        setArguments(args);
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        Vehicle currVehicle = vehicles.get(position);
                        confirmDelete(currVehicle, position);
                    }
                })
        );
    }

    private void confirmDelete(final Vehicle currVehicle, final int position){
        new AlertDialog.Builder(getActivity())
                .setTitle("Delete")
                .setMessage("Do you really want to delete " + currVehicle.getName() + " ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteVehicle(currVehicle, position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    private void deleteVehicle(Vehicle currVehicle, int position){
        AutoServiceDb db = new AutoServiceDb(context);
        db.deleteVehicle(currVehicle.getId());

        vehicles.remove(position);
        loadRecyclerList(formatListData(vehicles));
    }

    private void loadRecyclerList(String[][] list){
        recyclerAdapter = new RecyclerAdapter(list);
        vRecyclerView.swapAdapter(recyclerAdapter, false);
    }

    private void fillForm(Vehicle vehicle){
        editTextVehicleName.setText(vehicle.getName());
        editTextMileage.setText(String.format(Locale.US, "%d", vehicle.getMileage()));
        editTextMake.setText(vehicle.getMake());
        editTextModel.setText(vehicle.getModel());
        editTextYear.setText(String.format(Locale.US, "%d",vehicle.getYear()));
        editTextEngine.setText(vehicle.getEngine());
    }

    public void clearForm(){
        editTextVehicleName.setText("");
        editTextMileage.setText("");
        editTextMake.setText("");
        editTextModel.setText("");
        editTextYear.setText("");
        editTextEngine.setText("");
    }

    private boolean tryParseLong(String value){
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void addVehicle(){
        Vehicle vehicle = makeNewVehicle();
        new AutoServiceDb(context).insertVehicle(vehicle);
        vehicles.add(vehicle);
        loadRecyclerList(formatListData(vehicles));
    }

    public Vehicle makeNewVehicle(){
        Vehicle vehicle = new Vehicle();
        vehicle.setName(editTextVehicleName.getText().toString());
        vehicle.setMileage(Long.parseLong( editTextMileage.getText().toString()));
        vehicle.setMake(editTextMake.getText().toString());
        vehicle.setModel(editTextModel.getText().toString());
        vehicle.setYear(Long.parseLong(editTextYear.getText().toString()));
        vehicle.setEngine(editTextEngine.getText().toString());
        return vehicle;
    }
}
