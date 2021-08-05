package com.krish_the_dev.factorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public double factorial(int number) {
        double fact = 1;
        for(int i = 1; i <= number; i++) {
            fact *= i;
        }
        return fact;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView result = findViewById(R.id.result);
        TextView input = findViewById(R.id.input);
        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(v -> {
            int number = Integer.parseInt(input.getText().toString());
            result.setText(factorial(number) + "");
        });
    }
}