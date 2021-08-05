package com.krish_the_dev.peripherals;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    String[] pArray = {
            "Mouse",
            "Keyboard",
            "Webcam",
            "Microphone",
            "Monitor",
            "Speakers",
            "Projector",
            "Printer"};
    ArrayList<String> peripherals = new ArrayList<String>(Arrays.asList(pArray));

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_view, R.id.text, peripherals);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}