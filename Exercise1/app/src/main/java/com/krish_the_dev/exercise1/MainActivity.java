package com.krish_the_dev.exercise1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {
    float fontSize = 28;
    int[] colors = {Color.RED, Color.BLUE, Color.MAGENTA, Color.GREEN, Color.YELLOW};
    int[] fonts = {
            R.font.product_sans,
            R.font.circular_std,
            R.font.sf_pro,
            R.font.fira_sans
    };

    byte currentFont = 0;
    byte currentColor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView helloText = findViewById(R.id.textView);
        final Button changeSizeButton = findViewById(R.id.sizeButton);
        final Button changeColorButton = findViewById(R.id.colorButton);
        final Button changeFontButton = findViewById(R.id.fontButton);

        changeSizeButton.setOnClickListener(v -> {
            helloText.setTextSize(fontSize);
            fontSize += 4;
            if (fontSize == 52) { fontSize = 24; }
        });

        changeColorButton.setOnClickListener(v -> {
            helloText.setTextColor(colors[currentColor]);
            currentColor += 1;
            if (currentColor > colors.length - 1) { currentColor = 0; }
        });

        changeFontButton.setOnClickListener(v -> {
            helloText.setTypeface(getResources().getFont(fonts[currentFont]));
            currentFont += 1;
            if (currentFont >= colors.length - 1) { currentFont = 0; }
        });
    }
}
