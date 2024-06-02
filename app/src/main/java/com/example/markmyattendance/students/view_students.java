package com.example.markmyattendance.students;

import static java.util.Objects.isNull;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.markmyattendance.adapters.MyAdapter;
import com.example.markmyattendance.R;
import com.example.markmyattendance.database;
import com.example.markmyattendance.models.model;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class view_students extends AppCompatActivity implements MyAdapter.OnEditListener {

    public RecyclerView recyclerView;
    public RadioGroup radioGroup;
    public RadioButton radioButton;
    public Button UpdStud, deleteYes, deleteNo;
    public EditText editstudqid, editstudname, editstudphoneno;
    public TextInputLayout edit_student_qid, edit_student_name, edit_student_phoneno;
    public ImageView close;
    boolean isAllFieldsChecked = false;
    public ArrayList<model> dataholder = new ArrayList<>();
    public MyAdapter adapter;

    final ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }
        @Override
        public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,float dX, float dY,int actionState, boolean isCurrentlyActive){

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(view_students.this, R.color.red))
                    .addSwipeRightActionIcon(R.drawable.delete_icon)
                    .setActionIconTint(ContextCompat.getColor(view_students.this, R.color.black))
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(view_students.this, R.color.green))
                    .addSwipeLeftActionIcon(R.drawable.edit_icon)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getLayoutPosition();

            switch (direction) {
                case ItemTouchHelper.LEFT:

                    //Edit-Update DialogBox
                    editStudents(dataholder.get(position)); //Call the Interface method;
                    break;

                case ItemTouchHelper.RIGHT:

                    //Delete Dialog Box
                    AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(view_students.this);
                    View deleteview = LayoutInflater.from(view_students.this).inflate(R.layout.activity_delete_students,null);
                    deleteBuilder.setCancelable(false);
                    deleteBuilder.setView(deleteview);

                    AlertDialog alertDialogedelete = deleteBuilder.create();
                    alertDialogedelete.show();

                    deleteYes = deleteview.findViewById(R.id.deleteYes);
                    deleteNo = deleteview.findViewById(R.id.deleteNo);

                    deleteYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String studentid = dataholder.get(position).getStudent_id();
                            String d_resutls =  new database(getApplicationContext()).delete_students(studentid);
                            if(d_resutls == "yes"){
                                Toast.makeText(view_students.this, "Student Sucessfully Deleted", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(view_students.this, "Student not Delete, Try Again", Toast.LENGTH_SHORT).show();
                            }
                            dataholder.remove(position);
                            finish();
                            startActivity(getIntent());
                        }
                    });

                    deleteNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialogedelete.cancel();
                            finish();
                            startActivity(getIntent());
                        }
                    });
            }
        }
    };

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

        //- Attach ItemTouchHelper to RecyclerView of View Students Page..
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    // Show Students List...
    public void showdata(String datavalue) {
        dataholder.clear();
        Cursor cursor = new database(this).getStudents(datavalue);
        if (isNull(cursor)) {
            Toast.makeText(this, "No Record Found! (Error 404)", Toast.LENGTH_SHORT).show();
        }
        while (cursor.moveToNext()) {
            model obj = new model(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            dataholder.add(obj);
            MyAdapter adapter = new MyAdapter(dataholder,this::editStudents);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // Check All Validations...
    private boolean checkAllFields() {
        if(editstudqid.length()==0){
            edit_student_qid.setHelperText("Student Q-id is Required");
            return false;
        }else{
            if(editstudqid.length()<8){
                edit_student_qid.setHelperText("Please Enter 8 Digits Quantum-ID");
                return false;
            }else{
                edit_student_qid.setHelperText("");
            }
        }
        if(editstudname.length()==0){
            edit_student_name.setHelperText("Student Name is Required");
            return false;
        }else{
            if(editstudname.length()<3){
                edit_student_name.setHelperText("Student Name is Aleast 3 Characters");
                return false;
            }else {
                edit_student_name.setHelperText("");
            }
        }
        if(editstudphoneno.length()==0){
            edit_student_phoneno.setHelperText("Student Mobile No is Required");
            return false;
        }else{
            if(editstudphoneno.length()<10){
                edit_student_phoneno.setHelperText("Please Enter 10 Digits Mobile No");
                return false;
            }else{
                edit_student_phoneno.setHelperText("");
            }
        }
        edit_student_qid.setHelperText("");
        edit_student_name.setHelperText("");
        edit_student_phoneno.setHelperText("");
        return true;
    }

    // Edit-Update Students Function..
    @Override
    public void editStudents(model currentdata) {

        AlertDialog.Builder editBuilder = new AlertDialog.Builder(view_students.this);
        View editview = LayoutInflater.from(this).inflate(R.layout.activity_edit_students,null);
        editBuilder.setCancelable(false);
        editBuilder.setView(editview);

        AlertDialog alertDialogedit = editBuilder.create();
        alertDialogedit.show();

        close = editview.findViewById(R.id.editBoxClose);
        UpdStud = editview.findViewById(R.id.student_update);
        editstudqid = editview.findViewById(R.id.edit_student_id);
        editstudname = editview.findViewById(R.id.edit_student_name);
        editstudphoneno = editview.findViewById(R.id.edit_student_phone);
        edit_student_qid = editview.findViewById(R.id.edit_textinputlayout_qid);
        edit_student_name = editview.findViewById(R.id.edit_textinputlayout_name);
        edit_student_phoneno = editview.findViewById(R.id.edit_textinputlayout_phoneno);

        database std = new database(this);
        SQLiteDatabase db = std.getReadableDatabase();

        editstudqid.setText(currentdata.getStudent_qid());
        editstudname.setText(currentdata.getStudent_name());
        editstudphoneno.setText(currentdata.getStudent_phone());

        UpdStud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isAllFieldsChecked = checkAllFields();

                String studentId = currentdata.getStudent_id();
                String edit_student_q_id = editstudqid.getText().toString();
                String edit_student_name = editstudname.getText().toString();
                String edit_student_phone = editstudphoneno.getText().toString();

                if(isAllFieldsChecked == true) {
                    String e_results = std.update_students(studentId, edit_student_q_id, edit_student_name, edit_student_phone);
                    if (e_results == "yes") {
                        Toast.makeText(view_students.this, "Sucessfully Updated", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());

                    } else {
                        Toast.makeText(view_students.this, "Student not Update, try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogedit.cancel();
                finish();
                startActivity(getIntent());
            }
        });
    }
}
