package com.example.markmyattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button add_student,view_student,mark_attendance;
    ImageView image_attendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_student = findViewById(R.id.add_student);
        view_student = findViewById(R.id.view_student);
        mark_attendance = findViewById(R.id.mark_attendance);
        image_attendance = findViewById(R.id.image_attendance);

        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_student();
            }
        });
        view_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_student();
            }
        });
        mark_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mark_attendance();
            }
        });
        image_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mark_attendance();
            }
        });
    }
        public void add_student(){
            Intent intent = new Intent(this, add_students.class);
            startActivity(intent);
        }
        public void view_student(){
            Intent intent = new Intent(this, view_students.class);
            startActivity(intent);
        }
        public void mark_attendance(){
            Intent intent = new Intent(this, mark_attendance.class);
            startActivity(intent);
        }
}

