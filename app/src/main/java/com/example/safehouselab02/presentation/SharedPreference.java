package com.example.safehouselab02.presentation;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;

import timber.log.Timber;

public class SharedPreference {
    private SharedPreferences prefs;
    public static final String APP_PREFERENCES = "app_settings";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_DEFAULT_LANGUAGE = "language";

    public void init(FragmentActivity activity) {
        prefs = activity.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

    }

    public void setLanguage(String language) {
        prefs.edit().putString(KEY_DEFAULT_LANGUAGE, language).apply();
    }

    public void setUser(String email) {
        prefs.edit().putString(KEY_USER_EMAIL, email).apply();
    }

    public String getLanguage() {
        return prefs.getString(KEY_DEFAULT_LANGUAGE, "");
    }

    public String getUser() {
        return prefs.getString(KEY_USER_EMAIL, "");
    }

    public void removeUser() {
        Timber.e("TIMBER KEY _______________-------------------------");
        prefs.edit().remove(KEY_USER_EMAIL).apply();
    }
}
