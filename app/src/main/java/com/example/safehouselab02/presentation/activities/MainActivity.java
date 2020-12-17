package com.example.safehouselab02.presentation.activities;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.safehouselab02.R;
import com.example.safehouselab02.presentation.OnItemClick;
import com.example.safehouselab02.presentation.SharedPreference;
import com.example.safehouselab02.presentation.fragments.ProfileFragment;
import com.example.safehouselab02.presentation.fragments.SignInFragment;
import com.example.safehouselab02.presentation.fragments.SignUpFragment;
import com.example.safehouselab02.presentation.fragments.StartScreen;
import com.example.safehouselab02.presentation.ui_data.SensorViewData;



public class MainActivity extends BaseActivity implements
        OnItemClick,
        SignInFragment.OnButtonClickListener,
        SignUpFragment.OnClickListener,
        ProfileFragment.OnButtonClickListener {

    private SignUpFragment signUpFragment;
    private SignInFragment signInFragment;
    private StartScreen startScreenFragment;
    private ProfileFragment profileFragment;
    private boolean isSignedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLanguage();
        isSignedIn();
        setContentView(R.layout.activity_main);
        onAppStart();
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
        startScreenFragment = new StartScreen();
        if (!isSignedIn) {
            startSignInFragment();
        } else {
            startScreenFragment();
        }
    }


    private void isSignedIn() {
        SharedPreference sharedPreference = new SharedPreference();
        sharedPreference.init(this);
        String email = sharedPreference.getUser().trim();
        if (email.length() == 0) {
            isSignedIn = false;
        } else {
            isSignedIn = true;
        }
    }


    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem profile = menu.findItem(R.id.open_profile);
        MenuItem logOut = menu.findItem(R.id.logout);
        if(!isSignedIn)
        {
            profile.setVisible(false);
            logOut.setVisible(false);
        }
        else
        {
            profile.setVisible(true);
            logOut.setVisible(true);
        }
        return true;
    }

    @Override
    public void onItemClick(SensorViewData sensorViewData) {
        SensorActivity.start(this, sensorViewData);
    }
}