package com.krish_the_dev.filesearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class FileListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list2);

        TextView tv = findViewById(R.id.list);

        Intent state = getIntent();

        String mf = state.getStringExtra("matchingFiles");

        tv.setText(mf);
    }
}