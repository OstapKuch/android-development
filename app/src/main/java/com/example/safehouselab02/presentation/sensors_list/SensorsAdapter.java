package com.example.safehouselab02.presentation.sensors_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safehouselab02.R;
import com.example.safehouselab02.presentation.ui_data.SensorViewData;

import java.util.ArrayList;
import java.util.List;

public class SensorsAdapter extends RecyclerView.Adapter<SensorsViewHolder> {
    private final List<SensorViewData> userList = new ArrayList<>();

    public void setItems(List<SensorViewData> userList) {
        this.userList.clear();
        this.userList.addAll(userList);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public SensorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new SensorsViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorsViewHolder holder, int position) {
        holder.bindTo(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
