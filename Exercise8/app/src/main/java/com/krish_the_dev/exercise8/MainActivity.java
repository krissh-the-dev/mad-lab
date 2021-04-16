package com.krish_the_dev.exercise8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int BLOCK_SIZE = 100;

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

        EditText textInput = findViewById(R.id.inputElem);
        Button readBtn = findViewById(R.id.readBtn);
        Button writeBtn = findViewById(R.id.writeBtn);
        Button clearBtn = findViewById(R.id.clearBtn);

//        String path = Environment.getExternalStorageDirectory().toString() + "/sample.txt";
        String path = "sample.txt";

        readBtn.setOnClickListener(v -> {
            try {
                FileInputStream fileInputStream = openFileInput(path);
                InputStreamReader reader = new InputStreamReader(fileInputStream);

                char[] buffer = new char[BLOCK_SIZE];
                String fileContent = "";
                int charRead;

                while ((charRead = reader.read(buffer))>0) {
                    String readString = String.copyValueOf(buffer,0,charRead);
                    fileContent += readString;
                }
                reader.close();
                textInput.setText(fileContent);

                Toast.makeText(getApplicationContext(), "File read successfully", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error reading file", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

        writeBtn.setOnClickListener(v -> {
            try {
                String message = textInput.getText().toString();

                FileOutputStream fileOut = openFileOutput(path, MODE_PRIVATE);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileOut);
                outputWriter.write(message);
                outputWriter.close();

                Toast.makeText(getApplicationContext(), "File written successfully", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Error writing file", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();
            }
        });

        clearBtn.setOnClickListener(v -> {
            textInput.setText("");
            Toast.makeText(getApplicationContext(),"Text cleared", Toast.LENGTH_SHORT).show();
        });
    }
}
