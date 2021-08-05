package com.krish_the_dev.filesearch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 23;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    ArrayList<String> files = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        TextView searchField = findViewById(R.id.search);
        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(v -> {
            String searchString = searchField.getText().toString();
            File folder = Environment.getExternalStorageDirectory();

            for (File file : folder.listFiles()) {
                Log.d("App", file.getName());
                files.add(file.getName());
            }

            Pattern pattern = Pattern.compile(searchString);

            List<String> matching = files.stream()
                    .filter(pattern.asPredicate())
                    .collect(Collectors.toList());

            Intent state = new Intent(MainActivity.this, FileListActivity.class);

            String theMatches = "";
            for (int i = 0; i < matching.size(); i++)
            {
                theMatches += System.lineSeparator() + matching.get(i);
            }

            state.putExtra("matchingFiles", theMatches);

            startActivity(state);
        });

    }
}