package com.example.safehouselab02.presentation.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.safehouselab02.R;
import com.example.safehouselab02.presentation.view_models.NotificationViewModel;

import timber.log.Timber;


public class NotificationActivity extends AppCompatActivity {
    public static final String ID = "ID";
    private String id;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_notification);
        id = getIntent().getExtras().getString(ID);
        initViewModel();
    }


    private void initViewModel() {
        NotificationViewModel notificationViewModel = new NotificationViewModel();
        notificationViewModel.getResponse().observe(this, response -> {
            if(response != null) {
                SensorActivity.start(this, response);
            } else {
                Timber.e("SensorViewData object is empty");
            }

        });
        notificationViewModel.loadSensorById(id);
    }
}
