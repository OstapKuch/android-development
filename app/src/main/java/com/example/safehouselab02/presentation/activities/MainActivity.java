package com.example.safehouselab02.presentation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.safehouselab02.R;
import com.example.safehouselab02.presentation.fragments.SignInFragment;
import com.example.safehouselab02.presentation.fragments.SignUpFragment;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements
        SignInFragment.OnButtonClickListener,
        SignUpFragment.OnClickListener {

    private SignUpFragment signUpFragment;
    private SignInFragment signInFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.i("Timber: Started Main Activity");
        setContentView(R.layout.activity_main);
        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, signInFragment)
                .commit();
    }

    @Override
    public void onSignUpTextClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, signUpFragment)
                .commit();
    }

    @Override
    public void onSignInButtonClicked() {
        startActivity(new Intent(this,
                StartScreen.class));
    }

    @Override
    public void onSignUpButtonClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, signInFragment)
                .commit();
    }

    @Override
    public void onBackButtonClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, signInFragment)
                .commit();
    }
}