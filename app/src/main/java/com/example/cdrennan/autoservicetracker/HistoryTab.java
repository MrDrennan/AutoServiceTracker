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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class HistoryTab extends Fragment{

    private EditText editTextCost;
    private EditText editTextMileageOfService;
    private EditText editTextDateOfService;
    private EditText editTextNotes;

    private ArrayList<ServiceLog> serviceLogs;
    private RecyclerView hRecyclerView;
    private RecyclerView.LayoutManager hLayoutManager;
    private RecyclerView.Adapter recyclerAdapter;
    private EditText editTextServiceLogName;
    private View view;
    private Context context;

    public static HistoryTab newInstance(){
        return new HistoryTab();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.history_tab, container, false);

        editTextCost = view.findViewById(R.id.editTextCost);
        editTextMileageOfService = view.findViewById(R.id.editTextMileageOfService);
        editTextDateOfService = view.findViewById(R.id.editTextDateOfService);
        editTextNotes = view.findViewById(R.id.editTextNotes);
        editTextServiceLogName = view.findViewById(R.id.editTextServiceLogName);

        context = getActivity().getApplicationContext();
        AutoServiceDb db = new AutoServiceDb(context);

        setupRecycler(view, context, new String[0][0]);

        return view;
    }

    private String[][] formatListData(ArrayList<ServiceLog> serviceLogs){
        String[][] serviceLogInfo = new String[serviceLogs.size()][2];
        for (int i = 0; i < serviceLogs.size(); i++){
            serviceLogInfo[i][0] = serviceLogs.get(i).getDateOfService() + "";
            serviceLogInfo[i][1] = String.format(Locale.US, "$%.2f", serviceLogs.get(i).getCost());
        }
        return serviceLogInfo;
    }

    private void fillForm(ServiceLog serviceLog, long serviceId){
        fillForm(serviceLog);
        Service service = new AutoServiceDb(getActivity().getApplicationContext()).getService(serviceId);
        if (service == null){
            return;
        }
        editTextServiceLogName.setText(service.getName());
    }

    private void fillForm(ServiceLog serviceLog){
        editTextCost.setText(String.format(Locale.US, "$%.2f", serviceLog.getCost()));
        editTextMileageOfService.setText(String.format(Locale.US, "%d", serviceLog.getMileageOfService()));
        editTextDateOfService.setText(serviceLog.getDateOfService());
        editTextNotes.setText(serviceLog.getNotes());
    }

    public void clearForm(){
        editTextCost.setText("");
        editTextMileageOfService.setText("");
        editTextDateOfService.setText("");
        editTextNotes.setText("");
        editTextServiceLogName.setText("");
    }

    public void addServiceLog(long serviceId){
        ServiceLog serviceLog = makeNewService(serviceId);
        new AutoServiceDb(getActivity().getApplicationContext()).insertServiceLog(serviceLog);
        serviceLogs.add(serviceLog);
        //loadRecyclerList(formatListData(serviceLogs));
        String[][] list = formatListData(serviceLogs);
        setupRecycler(view, context, list);
    }

    public ServiceLog makeNewService(long serviceId){
        return new ServiceLog(0, serviceId,
                Double.parseDouble(editTextCost.getText().toString()),
                Long.parseLong(editTextMileageOfService.getText().toString()),
                editTextDateOfService.getText().toString(),
                editTextNotes.getText().toString());
    }

    public void loadData(long serviceId){
        serviceLogs = new AutoServiceDb(getActivity().getApplicationContext()).getServiceLogs(serviceId);
        if (serviceLogs != null && serviceLogs.size() > 0){
            fillForm(serviceLogs.get(0), serviceId);
            //loadRecyclerList(formatListData(serviceLogs));
            String[][] list = formatListData(serviceLogs);
            setupRecycler(view, context, list);
        }
        else {
            clearForm();
            //loadRecyclerList(new String[0][0]);
            String[][] list = formatListData(serviceLogs);
            setupRecycler(view, context, new String[0][0]);
        }
    }

    private void loadRecyclerList(String[][] list){
        recyclerAdapter = new RecyclerAdapter(list);
        hRecyclerView.swapAdapter(recyclerAdapter, false);
    }

    private void setupRecycler(View view, Context context, String[][] listData){
        hRecyclerView = view.findViewById(R.id.recyclerViewHistory);
        hRecyclerView.setHasFixedSize(true);

        hLayoutManager = new LinearLayoutManager(getActivity());
        hRecyclerView.setLayoutManager(hLayoutManager);

        recyclerAdapter = new RecyclerAdapter(listData);
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
    }
}
