package com.example.markmyattendance.students;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.markmyattendance.R;
import com.example.markmyattendance.database;
import com.google.android.material.textfield.TextInputLayout;

public class add_students extends AppCompatActivity {

    Button Addstud;
    EditText name,phone,q_id;
    TextInputLayout stud_qid,stud_name,stud_phoneno;
    boolean isAllFieldsChecked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_students);

        Addstud = findViewById(R.id.student_add);
        name = findViewById(R.id.student_name);
        phone = findViewById(R.id.student_phone);
        q_id = findViewById(R.id.student_id);
        stud_qid = findViewById(R.id.textinputlayout_qid);
        stud_name = findViewById(R.id.textinputlayout_name);
        stud_phoneno = findViewById(R.id.textinputlayout_phoneno);

        database std = new database(this);
        SQLiteDatabase db = std.getReadableDatabase();

        Addstud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isAllFieldsChecked = checkAllFields();

                String Student_q_id = q_id.getText().toString();
                String Student_name = name.getText().toString();
                String Student_phone = phone.getText().toString();

                if(isAllFieldsChecked == true) {
                    if (Student_q_id.equals("") && Student_name.equals("") && Student_phone.equals("")) {
                        Toast.makeText(add_students.this, "Enter all the Fields", Toast.LENGTH_SHORT).show();
                    } else {
                        String s_result = std.add_student(Student_q_id, Student_name, Student_phone);
                        if (s_result == "yes") {
                            q_id.setText("");
                            name.setText("");
                            phone.setText("");
                            Toast.makeText(add_students.this, "Student added Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            q_id.setText("");
                            name.setText("");
                            phone.setText("");
                            Toast.makeText(add_students.this, "Student not add , try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(add_students.this, "Please Validate All Input Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean checkAllFields() {
        if(q_id.length()==0){
            stud_qid.setHelperText("Student Q-id is Required");
            return false;
        }else{
            if(q_id.length()<8){
                stud_qid.setHelperText("Please Enter 8 Digits Quantum-ID");
                return false;
            }else{
                stud_qid.setHelperText("");
            }
        }
        if(name.length()==0){
            stud_name.setHelperText("Student Name is Required");
            return false;
        }else{
            if(name.length()<3){
                stud_name.setHelperText("Student Name is Aleast 3 Characters");
                return false;
            }else {
                stud_name.setHelperText("");
            }
        }
        if(phone.length()==0){
            stud_phoneno.setHelperText("Student Mobile No is Required");
            return false;
        }else{
            if(phone.length()<10){
                stud_phoneno.setHelperText("Please Enter 10 Digits Mobile No");
                return false;
            }else{
                stud_phoneno.setHelperText("");
            }
        }
        stud_qid.setHelperText("");
        stud_name.setHelperText("");
        stud_phoneno.setHelperText("");
        return true;
    }
}