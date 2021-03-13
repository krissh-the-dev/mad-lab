package com.krish_the_dev.exercise5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    NotificationManager nm;
    NotificationChannel notificationChannel;
    final String CHANNEL_ID = "krish_the_dev.general";

    Random rd = new Random();

    private void createNotification(String title, String message) {
        if(title.length() == 0 || message.length() == 0) {
            Toast.makeText(getApplicationContext(),
                    "Please enter all the details to create a notification",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_notification))
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (message.length() > 60) {
            builder.setStyle(new NotificationCompat.BigTextStyle());
        }

        nm.notify(rd.nextInt(), builder.build());

        Toast.makeText(getApplicationContext(), "Notification created", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nm = (NotificationManager) getSystemService(NotificationManager.class);

        TextView title = findViewById(R.id.notification_title);
        TextView message = findViewById(R.id.notification_message);

        Button btn = findViewById(R.id.createButton);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID,"General", NotificationManager.IMPORTANCE_DEFAULT);
            nm.createNotificationChannel(notificationChannel);
        }

        btn.setOnClickListener(v -> {
            createNotification(title.getText().toString().trim(), message.getText().toString().trim());
        });
    }
}
