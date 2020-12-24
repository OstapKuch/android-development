package com.example.safehouselab02.presentation.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

public class ConnectionStateListener extends LiveData<Boolean> {

    private final ConnectivityManager.NetworkCallback networkCallback;
    private final ConnectivityManager connectivityManager;

    public ConnectionStateListener(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkCallback = new NetworkCallback(this);
    }

    @Override
    protected void onActive() {
        super.onActive();
        updateConnection();
        connectivityManager.registerDefaultNetworkCallback(networkCallback);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }

    static class NetworkCallback extends ConnectivityManager.NetworkCallback {

        private final ConnectionStateListener mConnectionStateMonitor;

        public NetworkCallback(ConnectionStateListener connectionStateMonitor) {
            mConnectionStateMonitor = connectionStateMonitor;
        }

        @Override
        public void onAvailable(@NotNull Network network) {
            if(network != null) {
                mConnectionStateMonitor.postValue(true);
            }
        }

        @Override
        public void onLost(@NotNull Network network) {
            mConnectionStateMonitor.postValue(false);
        }
    }

    private void updateConnection() {
        if(connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            postValue(activeNetwork != null && activeNetwork.isConnectedOrConnecting());
        }

    }

    class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                updateConnection();
            }
        }
    }
}
