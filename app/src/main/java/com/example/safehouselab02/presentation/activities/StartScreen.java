package com.example.safehouselab02.presentation.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safehouselab02.R;
import com.example.safehouselab02.presentation.sensors_list.SensorsAdapter;
import com.example.safehouselab02.presentation.view_models.SensorsViewModel;

import timber.log.Timber;

public class StartScreen extends AppCompatActivity {

    private RecyclerView userList;
    private SensorsAdapter sensorsAdapter = new SensorsAdapter();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        showDialog();
        initRecycler();
        initViewModel();

    }

    @Override
    public void onBackPressed() {
    }

    private void showDialog() {
        String message = "You have successfully signed in";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle("Info");
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void initViewModel() {
        SensorsViewModel sensorsViewModel = new SensorsViewModel();
        sensorsViewModel.getErrorMessage().observe(this, errorMessage -> {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });
        sensorsViewModel.getResponse().observe(this, response -> {
            Timber.i("Timber we got response");
            sensorsAdapter.setItems(response);
        });
        sensorsViewModel.loadUserList();
    }

    private void initRecycler() {
        userList = findViewById(R.id.users_list);
        userList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        userList.setAdapter(sensorsAdapter);

    }
}