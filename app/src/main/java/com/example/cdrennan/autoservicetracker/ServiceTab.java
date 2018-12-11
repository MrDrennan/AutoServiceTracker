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
    private View view;
    private Context context;

    public static ServiceTab newInstance(){
        return new ServiceTab();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.service_tab, container, false);

        editTextServiceName = view.findViewById(R.id.editTextServiceName);
        editTextRemaining = view.findViewById(R.id.editTextRemaining);
        editTextMilesInterval = view.findViewById(R.id.editTextMilesInterval);
        editTextMonthsInterval = view.findViewById(R.id.editTextMonthsInterval);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        intervalSwitch = view.findViewById(R.id.switch1);
        textViewRemaining = view.findViewById(R.id.textViewRemaining);

        context = getActivity().getApplicationContext();
        AutoServiceDb db = new AutoServiceDb(context);
        service = db.getService(1);
        fillForm(service);

        services = db.getServices("Car");

        String[] serviceInfo = formatListData(services);
        setupRecycler(serviceInfo);

        intervalSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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

        boolean usesMonthsInterval = service.getUsesMonthsInterval() == Service.TRUE;
        intervalSwitch.setChecked(usesMonthsInterval);


    }

    private void clearForm(){
        editTextServiceName.setText("");
        editTextMilesInterval.setText("");
        editTextMonthsInterval.setText("");
        editTextDescription.setText("");
        boolean usesMonthsInterval = false;
        intervalSwitch.setChecked(usesMonthsInterval);
    }

    public void loadData(long vehicleId) {
        services = new AutoServiceDb(getActivity().getApplicationContext()).getServices(vehicleId);
        if (services != null && services.size() > 0){
            fillForm(services.get(0));
            loadRecyclerList(formatListData(services));
        }
        else{
            clearForm();
            loadRecyclerList(new String[0]);
        }
        Toast.makeText(context, "Vehicle Id: " + vehicleId, Toast.LENGTH_SHORT).show();

    }

    private String[] formatListData(ArrayList<Service> services){
        String[] serviceInfo = new String[services.size()];
        for (int i = 0; i < services.size(); i++){
            serviceInfo[i] = services.get(i).getName() + " " + services.get(i).getMilesLeft() + " miles left";
        }
        return serviceInfo;
    }

    private void loadRecyclerList(String[] list){
        recyclerAdapter = new RecyclerAdapter(list);
        sRecyclerView.swapAdapter(recyclerAdapter, true);
    }

    private void setupRecycler(String[] listData){

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
                        fillForm(services.get(position));
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                })
        );
    }
}
