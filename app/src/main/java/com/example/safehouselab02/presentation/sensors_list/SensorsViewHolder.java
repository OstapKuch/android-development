package com.example.safehouselab02.presentation.sensors_list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.safehouselab02.R;
import com.example.safehouselab02.presentation.listeners.OnItemClick;
import com.example.safehouselab02.presentation.ui_data.SensorViewData;

public class SensorsViewHolder extends RecyclerView.ViewHolder {
    private final OnItemClick clickListener;
    private final TextView userName;
    private final TextView userLogin;
    private final TextView userGender;

    private final ImageView userPhoto;

    public SensorsViewHolder(@NonNull View itemView, OnItemClick clickListener) {
        super(itemView);
        this.clickListener = clickListener;
        userName = itemView.findViewById(R.id.user_name);
        userLogin = itemView.findViewById(R.id.user_login);
        userGender = itemView.findViewById(R.id.user_gender);

        userPhoto = itemView.findViewById(R.id.user_image);
    }

    public void bindTo(SensorViewData sensorViewData) {
        userName.setText(sensorViewData.getName());
        userLogin.setText(sensorViewData.getTime());
        userGender.setText(sensorViewData.getState());


        Glide.with(userPhoto)
                .load(sensorViewData.getPhotoUrl())
                .centerCrop()
                .into(userPhoto);
    }
}
