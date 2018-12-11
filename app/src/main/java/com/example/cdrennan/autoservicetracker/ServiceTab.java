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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class ServiceTab extends Fragment{

    private EditText editTextServiceName;
    private EditText editTextRemaining;
    private EditText editTextMilesInterval;
    private EditText editTextMonthsInterval;
    private Switch intervalSwitch;
    private EditText editTextDescription;
    private TextView textViewRemaining;
    private Service service;
    private ArrayList<Service> services;
    private RecyclerView sRecyclerView;
    private RecyclerView.LayoutManager sLayoutManager;
    private RecyclerView.Adapter recyclerAdapter;
    private final String ARG_SERVICE_ID = "SERVICE_ID";

    public static ServiceTab newInstance(){
        return new ServiceTab();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.service_tab, container, false);

        editTextServiceName = view.findViewById(R.id.editTextServiceName);
        editTextRemaining = view.findViewById(R.id.editTextRemaining);
        editTextMilesInterval = view.findViewById(R.id.editTextMilesInterval);
        editTextMonthsInterval = view.findViewById(R.id.editTextMonthsInterval);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        intervalSwitch = view.findViewById(R.id.switch1);
        textViewRemaining = view.findViewById(R.id.textViewRemaining);

        Context context = getActivity().getApplicationContext();
        AutoServiceDb db = new AutoServiceDb(context);

        setupRecycler(view, context, new String[0][0]);

        intervalSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (service == null){
                    return;
                }
                if (isChecked){
                    textViewRemaining.setText(getString(R.string.days_left));
                    editTextRemaining.setText(String.format(Locale.US, "%d", service.getMonthsLeft()));
                }
                else{
                    textViewRemaining.setText(R.string.miles_left);
                    editTextRemaining.setText(String.format(Locale.US, "%d", service.getMilesLeft()));
                }
            }
        });

        return view;
    }

    private void fillForm(Service service){
        editTextServiceName.setText(service.getName());
        editTextMilesInterval.setText(String.format(Locale.US, "%d", service.getMilesInterval()));
        editTextMonthsInterval.setText(String.format(Locale.US, "%d", service.getMonthsInterval()));
        editTextDescription.setText(service.getDescription());

        boolean usesMonthsInterval = service.getUsesMonthsInterval();
        intervalSwitch.setChecked(usesMonthsInterval);

        if (usesMonthsInterval){
            textViewRemaining.setText(getString(R.string.days_left));
            editTextRemaining.setText(String.format(Locale.US, "%d", service.getMonthsLeft()));
        }
        else{
            textViewRemaining.setText(R.string.miles_left);
            editTextRemaining.setText(String.format(Locale.US, "%d", service.getMilesLeft()));
        }

    }

    public void clearForm(){
        editTextServiceName.setText("");
        editTextMilesInterval.setText("");
        editTextMonthsInterval.setText("");
        editTextDescription.setText("");
        editTextRemaining.setText("");
        boolean usesMonthsInterval = false;
        intervalSwitch.setChecked(usesMonthsInterval);
    }

    public void addService(long vehicleId){
        Service service = makeNewService(vehicleId);
        new AutoServiceDb(getActivity().getApplicationContext()).insertService(service);
        services.add(service);
        loadRecyclerList(formatListData(services));
    }

    public Service makeNewService(long vehicleId){
        return new Service(0, vehicleId,
                editTextServiceName.getText().toString(),
                Long.parseLong(editTextMilesInterval.getText().toString()),
                Long.parseLong(editTextMonthsInterval.getText().toString()),
                editTextDescription.getText().toString(),
                Long.parseLong(editTextMilesInterval.getText().toString()),
                Long.parseLong(editTextMonthsInterval.getText().toString()),
                intervalSwitch.isChecked() ? 1 : 0);
    }

    public void loadData(long vehicleId) {
        services = new AutoServiceDb(getActivity().getApplicationContext()).getServices(vehicleId);
        if (services != null && services.size() > 0){
            fillForm(services.get(0));
            loadRecyclerList(formatListData(services));
        }
        else{
            clearForm();
            loadRecyclerList(new String[0][0]);
        }
    }

    private String[][] formatListData(ArrayList<Service> services){
        String[][] serviceInfo = new String[services.size()][2];
        for (int i = 0; i < services.size(); i++){
            serviceInfo[i][0] = services.get(i).getName();

            if (services.get(i).getUsesMonthsInterval()){
                serviceInfo[i][1] = String.format(Locale.US, "%,d months left", services.get(i).getMonthsLeft());
            }
            else{
                serviceInfo[i][1] = String.format(Locale.US, "%,d miles left", services.get(i).getMilesLeft());
            }

        }
        return serviceInfo;
    }

    private void loadRecyclerList(String[][] list){
        recyclerAdapter = new RecyclerAdapter(list);
        sRecyclerView.swapAdapter(recyclerAdapter, true);
    }

    private void setupRecycler(View view, Context context, String[][] listData){

        sRecyclerView = view.findViewById(R.id.recyclerViewService);
        sRecyclerView.setHasFixedSize(true);

        sLayoutManager = new LinearLayoutManager(getActivity());
        sRecyclerView.setLayoutManager(sLayoutManager);

        recyclerAdapter = new RecyclerAdapter(listData);
        sRecyclerView.setAdapter(recyclerAdapter);

        DividerItemDecoration itemDecor = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecor.setDrawable(getContext().getResources().getDrawable(R.drawable.list_divider));
        sRecyclerView.addItemDecoration(itemDecor);

        sRecyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(context, sRecyclerView, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Service selectedService = services.get(position);
                        fillForm(services.get(position));
                        Bundle args = new Bundle();
                        args.putLong(ARG_SERVICE_ID, selectedService.getId());
                        setArguments(args);
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        Service service = services.get(position);
                        confirmDelete(service, position);
                    }
                })
        );
    }

    private void confirmDelete(final Service service, final int position){
        new AlertDialog.Builder(getActivity())
                .setTitle("Delete")
                .setMessage("Do you really want to delete " + service.getName() + " ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteVehicle(service, position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    private void deleteVehicle(Service service, int position){
        AutoServiceDb db = new AutoServiceDb(getActivity().getApplicationContext());
        db.deleteVehicle(service.getId());

        services.remove(position);
        loadRecyclerList(formatListData(services));
    }
}
