package com.krish_the_dev.exercise2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    String[] departments = {"CSE", "Mechanical", "Civil"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText nameInput = (EditText) findViewById(R.id.name);
        EditText registerNumberInput = (EditText) findViewById(R.id.reg_no);
        Spinner departmentSpinner = (Spinner) findViewById(R.id.dept);
        Button submitButton = (Button) findViewById(R.id.submit);

        departmentSpinner.setAdapter(new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_spinner_item,
                departments)
        );

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String registerNumber = registerNumberInput.getText().toString();
                String department = departmentSpinner.getSelectedItem().toString();

                Intent state = new Intent(MainActivity.this, ResultActivity.class);

                state.putExtra("name", name);
                state.putExtra("registerNumber", registerNumber);
                state.putExtra("department", department);

                startActivity(state);
            }
        });
    }
}
