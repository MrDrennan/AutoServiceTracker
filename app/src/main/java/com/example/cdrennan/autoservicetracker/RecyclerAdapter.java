package com.example.cdrennan.autoservicetracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RViewHolder> {
    private String[][] rDataset;

    public static class RViewHolder extends RecyclerView.ViewHolder {
        public TextView rTextViewCol1;
        public TextView rTextViewCol2;

        public RViewHolder(View v){
            super(v);
            rTextViewCol1 = v.findViewById(R.id.textViewCol1);
            rTextViewCol2 = v.findViewById(R.id.textViewCol2);
        }
    }

    public RecyclerAdapter(String[][] rDataset){
        this.rDataset = rDataset;
    }

    @Override
    public RecyclerAdapter.RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_item, parent,
                false);

        RViewHolder vh = new RViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RViewHolder holder, int position){
        holder.rTextViewCol1.setText(rDataset[position][0]);
        holder.rTextViewCol2.setText(rDataset[position][1]);
    }

    @Override
    public int getItemCount(){
        return rDataset.length;
    }
}
