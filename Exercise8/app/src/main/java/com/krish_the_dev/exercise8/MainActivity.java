package com.krish_the_dev.exercise8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText textInput = findViewById(R.id.inputElem);
        Button readBtn = findViewById(R.id.readBtn);
        Button writeBtn = findViewById(R.id.writeBtn);
        Button clearBtn = findViewById(R.id.clearBtn);

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("exercise8", Context.MODE_APPEND);
        File textFile = new File(directory, "sample" + ".txt");
        if (!textFile.exists()) {
            try {
                textFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        readBtn.setOnClickListener(v -> {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile));
                String bufferedText = "", currentLine;

                while ((currentLine = bufferedReader.readLine())!= null) {
                    bufferedText += currentLine;
                }

                textInput.setText(bufferedText);

                Toast.makeText(getApplicationContext(), "File read successfully", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error reading file", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

        writeBtn.setOnClickListener(v -> {
            try {
                String message = textInput.getText().toString();
                new BufferedWriter(new FileWriter(textFile)).write(message);

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
