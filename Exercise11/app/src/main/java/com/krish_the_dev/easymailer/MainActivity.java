package com.krish_the_dev.easymailer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView toAddressInput = findViewById(R.id.toAddress);
        TextView bodyInput = findViewById(R.id.body);
        TextView subjectInput = findViewById(R.id.subject);
        Button sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(v -> {
            String toAddress = toAddressInput.getText().toString();
            String subject = subjectInput.getText().toString();
            String messageBody = bodyInput.getText().toString();

            sendEmail(toAddress, subject, messageBody);
        });
    }

    private void sendEmail(String toAddress, String subject, String message) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

        emailIntent.setType("message/rfc822");
        emailIntent.setAction(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));

        emailIntent.putExtra(Intent.EXTRA_EMAIL, toAddress);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.addFlags(Intent.FLAG_FROM_BACKGROUND);

        try {
            startActivity(emailIntent);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "No email application detected", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }
}
