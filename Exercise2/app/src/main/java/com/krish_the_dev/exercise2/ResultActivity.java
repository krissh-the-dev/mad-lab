package com.krish_the_dev.exercise2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView nameText = findViewById(R.id.res_name);
        TextView regNoText = findViewById(R.id.res_reg_no);
        TextView deptText = findViewById(R.id.res_dept);
        Intent state = getIntent();

        nameText.setText(state.getStringExtra("name"));
        regNoText.setText(state.getStringExtra("registerNumber"));
        deptText.setText(state.getStringExtra("department"));
    }
}
