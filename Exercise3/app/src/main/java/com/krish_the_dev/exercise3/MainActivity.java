package com.krish_the_dev.exercise3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap image = Bitmap.createBitmap(dm.widthPixels, dm.heightPixels, Bitmap.Config.ARGB_8888);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageDrawable(new BitmapDrawable(getResources(), image));

        Canvas canvas = new Canvas(image);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(155, 66, 245));
        paint.setTextSize(60);

        canvas.drawText("Line", 250, 150, paint);
        canvas.drawLine(100,210,500, 210, paint);
        canvas.drawLine(100,211,500,211, paint); // just for thickening the line
        canvas.drawLine(100,212,500,212, paint); // just for thickening the line

        canvas.drawText("Square", 700, 150, paint);
        canvas.drawRect(600,200,1000,600, paint);

        canvas.drawText("Rectangle", 170, 400, paint);
        canvas.drawRect(100, 450, 500, 1250, paint);

        canvas.drawText("Ellipse", 730, 800, paint);
        canvas.drawOval(600,850,1000,1900, paint);

        canvas.drawText("Circle", 260, 1400, paint);
        canvas.drawOval(100, 1450, 550, 1900, paint);
    }
}
