package com.example.safehouselab02.presentation.controllers;

import android.app.Application;

import com.example.safehouselab02.BuildConfig;

import timber.log.Timber;

public class ApplicationController extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
