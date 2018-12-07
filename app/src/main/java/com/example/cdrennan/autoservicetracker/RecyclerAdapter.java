package com.example.cdrennan.autoservicetracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RViewHolder> {
    private String[] rDataset;

    public static class RViewHolder extends RecyclerView.ViewHolder {
        public TextView rTextView;

        public RViewHolder(View v){
            super(v);
            rTextView = v.findViewById(R.id.textViewVehicleInfo);
        }
    }

    public RecyclerAdapter(String[] rDataset){
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
        holder.rTextView.setText(rDataset[position]);
    }

    @Override
    public int getItemCount(){
        return rDataset.length;
    }
}
