package com.example.markmyattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.markmyattendance.mark_attendance.show_attendance_files;
import com.example.markmyattendance.students.add_students;
import com.example.markmyattendance.students.view_students;

public class MainActivity extends AppCompatActivity {

    CardView add_student,view_student,mark_attendance,show_pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_student = findViewById(R.id.add_student);
        view_student = findViewById(R.id.view_student);
        mark_attendance = findViewById(R.id.mark_attendance);
        show_pdf = findViewById(R.id.show_pdf);

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
        show_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_PDF();
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
            Intent intent = new Intent(this, com.example.markmyattendance.mark_attendance.mark_attendance.class);
            startActivity(intent);
        }
        public void show_PDF(){

//            String FolderName = "Attendance Files";
//            File filesPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
//            File files = new File(filesPath,FolderName);
//            int count = files.listFiles().length;
//            Log.d("FileCount",String.valueOf(count));
            Intent intent = new Intent(this, show_attendance_files.class);
            startActivity(intent);
        }
}

