package com.krish_the_dev.notificationlistener;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.Random;

public class NotificationListener extends NotificationListenerService {
    private boolean bound;
    NotificationManager nm;

    public boolean isBound() {
        return bound;
    }

    private void createNotification(String title, String message) {
        nm = getSystemService(NotificationManager.class);
        if(title.length() == 0 || message.length() == 0) {
            Toast.makeText(getApplicationContext(),
                    "Please enter all the details to create a notification",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_notification))
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (message.length() > 60) {
            builder.setStyle(new NotificationCompat.BigTextStyle());
        }

        nm.notify(123, builder.build());

        Toast.makeText(getApplicationContext(), "Notification created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();

        bound = true;
        MainActivity.status.setText("Active");
        MainActivity.status.setTextColor(Color.GREEN);
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();

        MainActivity.status.setText("Inactive");
        MainActivity.status.setTextColor(Color.RED);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        bound = false;
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        bound = true;
        super.onRebind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        if (sbn.getPackageName().equals(getPackageName())) return;

        Log.d("Package Name", sbn.getPackageName() + "");
        Log.d("ID", sbn.getId() + "");
        Log.d("My package", getPackageName() + "");

        createNotification("Notification detected", "New notification was posted by " + sbn.getPackageName());
        Toast.makeText(getApplicationContext(), "New Notification was posted", Toast.LENGTH_SHORT).show();
    }
}
