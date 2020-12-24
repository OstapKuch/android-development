package com.example.safehouselab02.presentation.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.safehouselab02.R;
import com.example.safehouselab02.presentation.ui_data.SensorViewData;


public class SensorActivity extends AppCompatActivity {

    public static final String PARCELABLE_KEY = "sensor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        initUI();
    }

    private void initUI() {
        SensorViewData sensorViewData = (SensorViewData) getIntent()
                .getParcelableExtra(PARCELABLE_KEY);

        TextView userName = findViewById(R.id.user_name);
        TextView userLogin = findViewById(R.id.user_login);
        TextView userGender = findViewById(R.id.user_gender);
        ImageView userPhoto = findViewById(R.id.user_image);
        userName.setText(sensorViewData.getName());
        userLogin.setText(sensorViewData.getTime());
        userGender.setText(sensorViewData.getState());
        Glide.with(userPhoto)
                .load(sensorViewData.getPhotoUrl())
                .centerCrop()
                .into(userPhoto);
    }

    public static void start(Context context, SensorViewData sensorViewData) {
        Intent starter = new Intent(context, SensorActivity.class);
        starter.putExtra(PARCELABLE_KEY, sensorViewData);
        context.startActivity(starter);
    }

}