package com.example.safehouselab02.presentation;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.safehouselab02.presentation.activities.NotificationActivity;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private final String ID = "CHANNEL_ID";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String sensorId = remoteMessage.getData().get("id");
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ID)
                .setSmallIcon(android.R.drawable.alert_light_frame)
                .setContentTitle("Sensors")
                .setContentText("New sensors")
                .setContentIntent(getClickActionIntent(sensorId))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, builder.build());

    }

    private PendingIntent getClickActionIntent(String id) {
        Intent notifyIntent = new Intent(this, NotificationActivity.class);
        notifyIntent.putExtra("ID", id);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        return PendingIntent.getActivity(
                this,
                0,
                notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

    }

    private void createNotificationChannel() {
        CharSequence name = ID;
        String description = "This is description";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public void onNewToken(@NotNull String token) {
        super.onNewToken(token);
    }

}
