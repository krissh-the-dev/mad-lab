package com.krish_the_dev.exercise4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    TextView regNo, studentName, marks;
    SQLiteDatabase db;

    private void createDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    private void clearForm() {
        regNo.setText("");
        studentName.setText("");
        marks.setText("");
    }

    private String getValue(TextView view) {
        return view.getText().toString().trim();
    }

    private Boolean isValid(TextView name, TextView regNo, TextView marks) {
        return getValue(name).length() != 0 &&
                getValue(regNo).length() != 0 &&
                getValue(marks).length() != 0;
    }

    private void insertStudent() {
        if(!isValid(studentName, regNo, marks)) {
            createDialog("Error", "Please enter all values");
            return;
        }
        db.execSQL("INSERT INTO student VALUES('" + getValue(regNo) + "','" + getValue(studentName) +
                "','" + getValue(marks) + "');");
        createDialog("Success", "Student inserted successfully");
    }

    private void updateStudent() {
        if(!isValid(studentName, regNo, marks)) {
            createDialog("Error", "Please enter all values");
            return;
        }

        Cursor c = db.rawQuery("SELECT * FROM student WHERE rollno='" + getValue(regNo) + "'", null);
        if(c.moveToFirst()) {
            db.execSQL("UPDATE student SET name='" + getValue(studentName) + "', marks='" + getValue(marks) +
                    "' WHERE rollno='"+ getValue(regNo) + "'");
            createDialog("Success", "Record Modified");
        }
        else {
            createDialog("Error", "Invalid Register Number");
        }

        clearForm();
    }

    private void deleteStudent() {
        if(getValue(regNo).length() == 0) {
            createDialog("Error", "Register number is required.");
            return;
        }

        Cursor c = db.rawQuery("SELECT * FROM student WHERE rollno='" + getValue(regNo) + "'", null);
        if(c.moveToFirst()) {
            db.execSQL("DELETE FROM student WHERE rollno='" + getValue(regNo) + "'");
            createDialog("Success", "Record deleted");
        }
        else {
            createDialog("Error", "Invalid Register Number");
        }

        clearForm();
    }

    private void viewStudent() {
        if(getValue(regNo).length()==0) {
            createDialog("Error", "Register number is required");
            return;
        }

        Cursor c = db.rawQuery("SELECT * FROM student WHERE rollno='" + getValue(regNo) + "'", null);
        if(c.moveToFirst()) {
            studentName.setText(c.getString(1));
            marks.setText(c.getString(2));
        }
        else {
            createDialog("Error", "Invalid Register Number");
        }
    }

    private void viewAll() {
        Cursor c = db.rawQuery("SELECT * FROM student", null);
        if(c.getCount() == 0 ) {
            createDialog("Error", "No records found");
            return;
        }

        StringBuffer buffer=new StringBuffer();
        while(c.moveToNext()) {
            buffer.append("Reg. No: " + c.getString(0) + "\n");
            buffer.append("Name: " + c.getString(1) + "\n");
            buffer.append("Marks: " + c.getString(2) + "\n\n");
        }
        createDialog("Student Details", buffer.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regNo = findViewById(R.id.reg_no);
        studentName = findViewById(R.id.name);
        marks = findViewById(R.id.marks);

        Button insertButton = findViewById(R.id.insertButton);
        Button updateButton = findViewById(R.id.updateButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button clearButton = findViewById(R.id.clear);
        Button viewButton = findViewById(R.id.viewButton);
        Button viewAllButton = findViewById(R.id.viewAllButton);

        insertButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        viewButton.setOnClickListener(this);
        viewAllButton.setOnClickListener(this);

        db = openOrCreateDatabase("students", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno VARCHAR, name VARCHAR, marks VARCHAR);");
    }

    @Override
    public void onClick(View v) {
        Button cause = (Button) v;
        switch(cause.getText().toString()) {
            case "Insert":
                insertStudent();
                clearForm();
                break;

            case "Update":
                updateStudent();
                break;

            case "Delete":
                deleteStudent();
                break;

            case "Clear":
                clearForm();
                break;

            case "View":
                viewStudent();
                break;

            case "View All":
                viewAll();
                break;

            default: break;
        }
    }
}
