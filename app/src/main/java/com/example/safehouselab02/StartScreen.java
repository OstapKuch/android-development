package com.example.safehouselab02;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import timber.log.Timber;

public class StartScreen extends AppCompatActivity {
    private Dialog dialog;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_start_screen);
        TextView text = dialog.findViewById(R.id.dialog_info);
        text.setText("You have successfully signed in");
        dialog.show();
        Timber.i("Timber log test");


    }
}