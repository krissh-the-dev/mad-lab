package com.krish_the_dev.exercise6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    public static int progress = 0;
    public static ProgressBar progressBar;
    ProgressHandler progressHandler;

    NotificationChannel notificationChannel;
    static NotificationManager notificationManager;
    public static NotificationCompat.Builder builder;
    final private String CHANNEL_ID = "krish_the_dev.general";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        Button pauseBtn = findViewById(R.id.pauseBtn);
        Button startBtn = findViewById(R.id.startBtn);

        notificationManager = (NotificationManager) getSystemService(NotificationManager.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            progressBar.setMin(0);
            progressBar.setMax(100);

            notificationChannel = new NotificationChannel(CHANNEL_ID,"General", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setContentTitle("Sample progress")
                .setSmallIcon(R.drawable.ic_notification)
                .setPriority(NotificationCompat.PRIORITY_LOW);
        progressHandler = new ProgressHandler();

        startBtn.setOnClickListener(b -> {
            progressHandler.toggleThreadState();
            if(progressHandler.isLive()) {
                startBtn.setText("Stop");
                pauseBtn.setText("Pause");
                pauseBtn.setEnabled(true);
            } else {
                progressHandler = new ProgressHandler();
                pauseBtn.setEnabled(false);
                startBtn.setText("Start");
            }
        });

        pauseBtn.setOnClickListener(b -> {
            progressHandler.toggleRunningState();
            if(progressHandler.isRunning()) {
                pauseBtn.setText("Pause");
            } else {
                pauseBtn.setText("Resume");
            }
        });
    }
}

class ProgressHandler extends Thread {
    private boolean alive = false;
    private boolean isRunning = false;

    public boolean isLive() {
        return alive;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean toggleThreadState() {
        alive = !alive;
        isRunning = true;
        if(alive) this.start();
        else {
            MainActivity.progress = 0;
            MainActivity.notificationManager.cancel(0);
        }
        return isLive();
    }

    public boolean toggleRunningState() {
        isRunning = !isRunning();
        return isRunning();
    }

    @Override
    public void run() {
        try {
            for (MainActivity.progress = 0; MainActivity.progress <= 100 && alive; MainActivity.progress++) {
                while(!isRunning()) {
                    Thread.sleep(1);
                }
                Thread.sleep(100);
                MainActivity.progressBar.setProgress(MainActivity.progress, true);
                MainActivity.builder.setProgress(100, MainActivity.progress, false);
                MainActivity.notificationManager.notify(0, MainActivity.builder.build());
            }

                MainActivity.builder.setProgress(0, 0, false);
                MainActivity.notificationManager.notify(0, MainActivity.builder.build());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
