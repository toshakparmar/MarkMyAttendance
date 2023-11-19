package com.example.markmyattendance;

import static java.util.Objects.isNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class view_students extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RadioGroup radioGroup;
    public RadioButton radioButton;
    public ArrayList<model> dataholder = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        recyclerView = findViewById(R.id.student_list);
        radioGroup = findViewById(R.id.radioGroup);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showdata("Name");
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioid = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioid);
                switch (checkedId) {
                    case R.id.nameWise:
                        showdata("Name");
                        break;
                    case R.id.qidWise:
                        showdata("Q-id");
                        break;
                }
            }
        });
    }
    public void showdata(String datavalue) {
        dataholder.clear();
        Cursor cursor = new database(this).getStudents(datavalue);
        if (isNull(cursor)) {
            Toast.makeText(this, "No Record Found! (Error 404)", Toast.LENGTH_SHORT).show();
        }
        while (cursor.moveToNext()) {
            model obj = new model(cursor.getString(1), cursor.getString(2), cursor.getString(3));
            dataholder.add(obj);
            MyAdapter adapter = new MyAdapter(dataholder);
            recyclerView.setAdapter(adapter);
        }
    }
}