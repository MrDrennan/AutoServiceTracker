package com.example.cdrennan.autoservicetracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Locale;

public class HistoryTab extends Fragment{

    private EditText editTextCost;
    private EditText editTextMileageOfService;
    private EditText editTextDateOfService;
    private EditText editTextNotes;
    private ServiceLog serviceLog;

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

        return view;
    }

    private void fillForm(ServiceLog serviceLog){
        editTextCost.setText(String.format(Locale.US, "$%.2f", serviceLog.getCost()));
        editTextMileageOfService.setText(String.format(Locale.US, "%d", serviceLog.getMileageOfService()));
        editTextDateOfService.setText(serviceLog.getDateOfService());
        editTextNotes.setText(serviceLog.getNotes());
    }
}
