package com.example.safehouselab02.presentation.activities;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.safehouselab02.Dialog;
import com.example.safehouselab02.R;
import com.example.safehouselab02.presentation.fragments.ProfileFragment;
import com.example.safehouselab02.presentation.fragments.SignInFragment;
import com.example.safehouselab02.presentation.fragments.SignUpFragment;
import com.example.safehouselab02.presentation.fragments.StartScreenFragment;
import com.example.safehouselab02.presentation.listeners.ConnectionStateListener;
import com.example.safehouselab02.presentation.listeners.OnItemClick;
import com.example.safehouselab02.presentation.shared_prefs.SharedPreference;
import com.example.safehouselab02.presentation.ui_data.SensorViewData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends BaseActivity implements
        OnItemClick,
        SignInFragment.OnButtonClickListener,
        SignUpFragment.OnClickListener,
        ProfileFragment.OnButtonClickListener {
    public static final String ID = "ID";

    private SignUpFragment signUpFragment;
    private SignInFragment signInFragment;
    private StartScreenFragment startScreenFragment;
    private ProfileFragment profileFragment;
    private boolean isSignedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLanguage();
        isSignedIn();
        initFirebase();

        setContentView(R.layout.activity_main);
        onAppStart();
        setConnectionMonitoring();
    }

    private void setConnectionMonitoring() {
        ConnectionStateListener connectionStateMonitor = new ConnectionStateListener(this);
        Dialog dialog = new Dialog();
        final String dialogTitle = "Notification";
        final String dialogMessage = "Network connection was lost.";
        connectionStateMonitor.observe(this, aBoolean -> {
            if(!aBoolean) {
                // network connection lost
                dialog.showDialog(dialogTitle, dialogMessage, this);
            }
        });
    }

    @Override
    public void onSignUpTextClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, signUpFragment)
                .commit();
    }


    @Override
    public void onSignInButtonClicked() {
        isSignedIn();
        startScreenFragment();
    }

    @Override
    public void onSignUpButtonClicked() {

        startSignInFragment();
    }


    @Override
    public void onBackButtonClicked() {
        startSignInFragment();
    }


    @Override
    public void onProfileClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, profileFragment)
                .commit();
    }


    @Override
    public void onSaveButtonClicked() {
        startScreenFragment();
    }


    private void startSignInFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, signInFragment)
                .commit();
    }


    private void startScreenFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, startScreenFragment)
                .commit();
    }


    private void onAppStart() {
        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();
        profileFragment = new ProfileFragment();
        startScreenFragment = new StartScreenFragment();
        if(!isSignedIn) {
            startSignInFragment();
        } else {
            startScreenFragment();
        }
    }


    private void isSignedIn() {
        SharedPreference sharedPreference = new SharedPreference();
        sharedPreference.init(this);
        String email = sharedPreference.getUser().trim();
        isSignedIn = email.length() != 0;
    }


    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem profile = menu.findItem(R.id.open_profile);
        MenuItem logOut = menu.findItem(R.id.logout);
        if(!isSignedIn) {
            profile.setVisible(false);
            logOut.setVisible(false);
        } else {
            profile.setVisible(true);
            logOut.setVisible(true);
        }
        return true;
    }

    @Override
    public void onItemClick(SensorViewData sensorViewData) {
        SensorActivity.start(this, sensorViewData);
    }

    public void initFirebase() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        String token = task.getResult();
                    }
                });

    }





}