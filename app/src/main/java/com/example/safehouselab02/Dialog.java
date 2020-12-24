package com.example.safehouselab02;

import android.app.AlertDialog;
import android.content.Context;

public class Dialog {

    public Dialog() {
    }

    public void showDialog(String title, String message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
