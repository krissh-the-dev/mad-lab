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
import android.view.View;
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
    private static final int REQUEST_EXTERNAL_STORAGE = 23;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
    File file = new File(folder, "sample.txt");

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

        readBtn.setOnClickListener(v -> {
            try {
                String fileContent = readFile(file);
                textInput.setText(fileContent);
                Toast.makeText(getApplicationContext(), "File read successfully", Toast.LENGTH_SHORT).show();
            } catch (IOException exp) {
                exp.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error reading file", Toast.LENGTH_SHORT).show();
            }
        });

        writeBtn.setOnClickListener(v -> {
            try {
                String message = textInput.getText().toString();
                writeTextData(file, message);
                textInput.setText("");
                Toast.makeText(getApplicationContext(), "File write successful", Toast.LENGTH_SHORT).show();
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

    private String readFile(File file) throws IOException{
        if(!file.exists()) {
            Toast.makeText(getApplicationContext(),
                    "File does not exist, create one first.",
                    Toast.LENGTH_SHORT).show();
            return "";
        }

        FileReader fileReader = new FileReader(file);

        int byteOfData;
        String accumulator = "";
        while ((byteOfData = fileReader.read()) != -1) {
            accumulator += (char)byteOfData;
        }
        fileReader.close();
        return accumulator;
        
    }

    private void writeTextData(File file, String data) throws IOException{
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(data.getBytes());
        fileOutputStream.close();
    }
}
