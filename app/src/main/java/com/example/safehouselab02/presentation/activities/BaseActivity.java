package com.example.safehouselab02.presentation.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.safehouselab02.R;
import com.example.safehouselab02.presentation.SharedPreference;

import java.util.Locale;


public class BaseActivity extends AppCompatActivity {
    private SharedPreference sharedPreference;
    private Configuration config;
    private final String LANGUAGE_US = "English";
    private final String LANGUAGE_UA = "Ukrainian";

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_language:
                onLanguageClicked();
                return true;
            case R.id.open_profile:
                onProfileClicked();
                return true;
            case R.id.logout:
                onLogOutClicked();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initSharedPreference() {
        config = new Configuration();
        sharedPreference = new SharedPreference();
        sharedPreference.init(this);
    }

    public void initLanguage() {
        initSharedPreference();
        String language = sharedPreference.getLanguage();
        if (language.equals(LANGUAGE_UA)) {
            setUkrainian(config);
        } else if (!language.equals(LANGUAGE_US)) {
            sharedPreference.setLanguage(LANGUAGE_US);
        }
    }

    private void onLanguageClicked() {
        initSharedPreference();
        String language = sharedPreference.getLanguage();
        if (language.equals(LANGUAGE_UA)) {
            sharedPreference.setLanguage(LANGUAGE_US);
            this.getApplicationContext().getResources().updateConfiguration(config, null);
        } else if (language.equals(LANGUAGE_US)) {
            sharedPreference.setLanguage(LANGUAGE_UA);
            setUkrainian(config);
        }
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    private void onLogOutClicked() {
        initSharedPreference();
        sharedPreference.removeUser();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void setUkrainian(Configuration config) {
        Locale locale;
        final String lang = "ua";
        final String country = "UA";
        locale = new Locale(lang, country);
        Locale.setDefault(locale);
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    public void onProfileClicked() {
    }


}
