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

public class HistoryTab extends Fragment{

    private EditText editTextCost;
    private EditText editTextMileageOfService;
    private EditText editTextDateOfService;
    private EditText editTextNotes;
    private ServiceLog serviceLog;
    private ArrayList<ServiceLog> serviceLogs;
    private RecyclerView hRecyclerView;
    private RecyclerView.LayoutManager hLayoutManager;
    private RecyclerView.Adapter recyclerAdapter;

    public static HistoryTab newInstance(){
        return new HistoryTab();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_tab, container, false);

        editTextCost = view.findViewById(R.id.editTextCost);
        editTextMileageOfService = view.findViewById(R.id.editTextMileageOfService);
        editTextDateOfService = view.findViewById(R.id.editTextDateOfService);
        editTextNotes = view.findViewById(R.id.editTextNotes);

        Context context = getActivity().getApplicationContext();
        AutoServiceDb db = new AutoServiceDb(context);

        serviceLog = new ServiceLog(1L, 1L, 50.5, 100005L, "1/12/18", "small oil leak");
        db.insertServiceLog(serviceLog);
        ServiceLog serviceLog2 = db.getServiceLog(1);
        fillForm(serviceLog2);

        serviceLogs = db.getServiceLogs("oil change");
        String[] serviceLogInfo = new String[serviceLogs.size()];
        int i = 0;
        for(ServiceLog serviceLog : serviceLogs){
            serviceLogInfo[i] = serviceLog.getDateOfService() + " $" + serviceLog.getCost();
            i++;
        }

        hRecyclerView = view.findViewById(R.id.recyclerViewHistory);
        hRecyclerView.setHasFixedSize(true);

        hLayoutManager = new LinearLayoutManager(getActivity());
        hRecyclerView.setLayoutManager(hLayoutManager);

        recyclerAdapter = new RecyclerAdapter(serviceLogInfo);
        hRecyclerView.setAdapter(recyclerAdapter);

        DividerItemDecoration itemDecor = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecor.setDrawable(getContext().getResources().getDrawable(R.drawable.list_divider));
        hRecyclerView.addItemDecoration(itemDecor);

        hRecyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(context, hRecyclerView, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        fillForm(serviceLogs.get(position));
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                })
        );

        return view;
    }

    private void fillForm(ServiceLog serviceLog){
        editTextCost.setText(String.format(Locale.US, "$%.2f", serviceLog.getCost()));
        editTextMileageOfService.setText(String.format(Locale.US, "%d", serviceLog.getMileageOfService()));
        editTextDateOfService.setText(serviceLog.getDateOfService());
        editTextNotes.setText(serviceLog.getNotes());
    }

    public void loadData(long serviceId){

    }
}
