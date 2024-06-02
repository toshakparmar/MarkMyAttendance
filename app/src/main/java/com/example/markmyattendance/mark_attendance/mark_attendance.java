package com.example.markmyattendance.mark_attendance;

import static java.nio.file.Files.createDirectory;
import static java.util.Objects.isNull;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.markmyattendance.adapters.MyAdapter;
import com.example.markmyattendance.adapters.MyAdapter2;
import com.example.markmyattendance.R;
import com.example.markmyattendance.database;
import com.example.markmyattendance.models.model;
import com.example.markmyattendance.students.view_students;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class mark_attendance extends AppCompatActivity implements MyAdapter2.checkboxCount {

    public RecyclerView recyclerView2;
    public Button presentAttendance, absentAttendance;
    public TextView showCheckboxCount;
    public CheckBox selectAll;
    public ArrayList<model> dataholder2 = new ArrayList<>();
    public MyAdapter2 adapter2;
    public String lectureName;
    public String selectedStudents1 = "", selectedStudents2 = "";
    public boolean attendanceStatus = false;
    public int studentcount = 0, allStudentsCount = 0;

    //Current-Date
    Date date = new Date();
    CharSequence todayDate  = DateFormat.format("MMMM d, yyyy ", date.getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);
        recyclerView2 = findViewById(R.id.mark_attendance_view);
        presentAttendance = findViewById(R.id.present_attendance);
        absentAttendance = findViewById(R.id.absent_attendance);
        selectAll = findViewById(R.id.selectAll);
        showCheckboxCount = findViewById(R.id.countSelectedStudents);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        // UserPermission for Access External Storage
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        //Default Show the Students through Name wise...
        markAttendance("Name");

        //Select all Checkbox Functionality..
        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectAll.isChecked()){
                    adapter2.selectAllCheckbox();
                    showCheckboxCount.setText(String.valueOf(dataholder2.size()));
                    studentcount = dataholder2.size();
                }else{
                    adapter2.unselectAllCheckbox();
                    showCheckboxCount.setText("");
                    studentcount = 0;
                }
                adapter2.notifyDataSetChanged();
            }
        });

        // Get the allStudents and markedStudents by checkbox...
        ArrayList<String> markedStudents = adapter2.getMarkedStudentsList();
        ArrayList<String> allStudents = adapter2.getAllStudentsList();

        // Click on Present Button...
        presentAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendanceStatus = true;
                getStudentsData(markedStudents,allStudents);
            }
        });

        // Click on Absent Button...
        absentAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendanceStatus = false;
                getStudentsData(markedStudents,allStudents);
            }
        });
    }

    private void getStudentsData(ArrayList<String> markedStudents, ArrayList<String> allStudents) {
        selectedStudents1 = "";
        selectedStudents2 = "";
        studentcount = 0;
        if(selectAll.isChecked() && markedStudents.isEmpty()){
            int sno = 1;
            studentcount = dataholder2.size();
            for (int j = 0; j < dataholder2.size(); j++) {
                if(j<=39) {
                    selectedStudents1 += sno + ". " + dataholder2.get(j).getStudent_name() + "\n";
                }else{
                    selectedStudents2 += sno + ". " + dataholder2.get(j).getStudent_name() + "\n";
                }
                sno++;
            }


            takeLectureName(selectedStudents1, selectedStudents2, attendanceStatus);
        }else {
            if(markedStudents.isEmpty()){
                Toast.makeText(mark_attendance.this,"You did not Select Students for Marking the Attendance ?",Toast.LENGTH_LONG).show();
            }else {
                int sno = 1, uno = 1;
                studentcount = markedStudents.size();
                for (int j = 0; j < markedStudents.size(); j++) {
                    if(j<=39) {
                        selectedStudents1 += sno + ". " + markedStudents.get(j).toString() + "\n";
                    }else{
                        selectedStudents2 += sno + ". " + markedStudents.get(j).toString() + "\n";
                    }
                    sno++;
                }
                takeLectureName(selectedStudents1, selectedStudents2, attendanceStatus);
            }
        }
    }

    //Take Leacture Name...
    private void takeLectureName(String selectedAttendance1, String selectedAttendance2, boolean attendanceStatus) {
        Button submitSubjectName, cancelSubjectName;
        EditText take_subjectName;
        TextInputLayout take_subjectNameHelper;

        AlertDialog.Builder takeSubjectNameBuilder = new AlertDialog.Builder(mark_attendance.this);
        View takeSubjectNameView = LayoutInflater.from(mark_attendance.this).inflate(R.layout.take_input_subject_pdf_name,null);
        takeSubjectNameBuilder.setCancelable(false);
        takeSubjectNameBuilder.setView(takeSubjectNameView);

        AlertDialog alertDialogtakeSubjectName = takeSubjectNameBuilder.create();
        alertDialogtakeSubjectName.show();

        take_subjectName = takeSubjectNameView.findViewById(R.id.take_input_subject_name);
        take_subjectNameHelper = takeSubjectNameView.findViewById(R.id.textinputlayout_subject_name);
        submitSubjectName = takeSubjectNameView.findViewById(R.id.submit_subject_name);
        cancelSubjectName = takeSubjectNameView.findViewById(R.id.cancel_subject_name);

        submitSubjectName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lectureName = take_subjectName.getText().toString();
                if(lectureName.length() == 0){
                    take_subjectNameHelper.setHelperText("Please Enter Lecture/Subject Name...");
                }else{
                    createAttendancePDF_XML(selectedAttendance1, selectedAttendance2, lectureName, attendanceStatus);
                    alertDialogtakeSubjectName.cancel();
                }
            }
        });
        cancelSubjectName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogtakeSubjectName.cancel();
            }
        });
    }

    // Create Function through Attendance PDF XML File..
    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    private void createAttendancePDF_XML(String selectedStudents1, String selectedStudents2, String takeLectureName, boolean attendanceStatus) {

        TextView pdfDate, pdfSubject, classRoom, studentList1, studentList2, title;

        // Inflate the layout
        View view = LayoutInflater.from(this).inflate(R.layout.attendance_pdf_template,null);
            pdfDate = view.findViewById(R.id.pdfDate);
            pdfSubject = view.findViewById(R.id.pdfSubject);
            classRoom = view.findViewById(R.id.classRoom);
            studentList1 = view.findViewById(R.id.studentList1);
            studentList2 = view.findViewById(R.id.studentList2);
            title = view.findViewById(R.id.title);

            // Take the Pdf Name and Subject Name..
            AlertDialog.Builder takeSubjectNameBuilder = new AlertDialog.Builder(mark_attendance.this);
            View takeSubjectNameView = LayoutInflater.from(mark_attendance.this).inflate(R.layout.take_input_subject_pdf_name,null);
            takeSubjectNameBuilder.setCancelable(false);
            takeSubjectNameBuilder.setView(takeSubjectNameView);

            pdfDate.setText("Date: "+ todayDate);
            pdfSubject.setText("Subject: "+ takeLectureName);
            classRoom.setText("Class: MCA");

            //Set Student List Title and Value..
            if (attendanceStatus) {
                //Insert Presented Students..
                title.setText("Present Students List - " + studentcount + "/" + dataholder2.size());
                title.setTextColor(Color.parseColor("#101E78"));
                studentList1.setText(selectedStudents1);
                studentList2.setText(selectedStudents2);
                studentList1.setTextColor(Color.parseColor("#101E78"));
                studentList2.setTextColor(Color.parseColor("#101E78"));
            } else {
                //Insert Absented Students..
                title.setText("Absent Students List - " + studentcount + "/" + dataholder2.size());
                title.setTextColor(Color.parseColor("#101E78"));
                studentList1.setText(selectedStudents1);
                studentList2.setText(selectedStudents2);
                studentList1.setTextColor(Color.parseColor("#101E78"));
                studentList2.setTextColor(Color.parseColor("#101E78"));
            }



        DisplayMetrics displayMetrics = new DisplayMetrics();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            this.getDisplay().getRealMetrics(displayMetrics);
        }else{
            this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }
        
        view.measure(View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, View.MeasureSpec.EXACTLY));

        view.layout(0,0, displayMetrics.widthPixels, displayMetrics.heightPixels);

        int viewWidth = view.getMeasuredWidth();
        int viewHeight = view.getMeasuredHeight();

        //Create PDF File..
        PdfDocument attendancePDF = new PdfDocument();
        PdfDocument.PageInfo attendancePageInfo = new PdfDocument.PageInfo.Builder(viewWidth,viewHeight,1).create();
        PdfDocument.Page attendancePage = attendancePDF.startPage(attendancePageInfo);

        //Create Canvas to Print XML File..
        Canvas canvas = attendancePage.getCanvas();
        view.draw(canvas);

        //Branding
        Paint pdfPaint = new Paint();
        pdfPaint.setTextAlign(Paint.Align.CENTER);
        pdfPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        pdfPaint.setTextSize(30);
        pdfPaint.setColor(Color.BLACK);
        canvas.drawText("Powered By @CodeSmachers",viewWidth/2,viewHeight-50,pdfPaint);

        attendancePDF.finishPage(attendancePage);

        String folderName = "AttendanceFiles";
        File filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File folder = new File(filePath,folderName);
        if(!folder.exists()){
            boolean isFolder = folder.mkdirs();
            if(isFolder){
                Toast.makeText(mark_attendance.this,"Attendance Folder Created Sucessfully!",Toast.LENGTH_SHORT).show();
                genratePDF(takeLectureName, attendancePDF, folder);
            }else{
                Toast.makeText(mark_attendance.this,"Attendance Folder didn't Created?",Toast.LENGTH_SHORT).show();
            }
        }else{
            genratePDF(takeLectureName, attendancePDF, folder);
        }
    }

    //Generate PDF inside MCA Folder..
    private void genratePDF(String takeLectureName,PdfDocument attendancePDF,File folder) {
        String fileName = takeLectureName+"_"+todayDate+".pdf";
        File pdfFile = new File(folder, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(pdfFile);
            attendancePDF.writeTo(fos);
            attendancePDF.close();
            fos.close();
            Toast.makeText(mark_attendance.this, takeLectureName+" Attendance-PDF Generated Successfully", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("Exception Errors: ",e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Exception Errors: ",e.toString());
        }
    }

    // Create function through Scratch using PDFDocument Class..
    private void createAttendancePDF(ArrayList<String> presentStudents) {
        if(presentStudents.isEmpty()){
            Toast.makeText(mark_attendance.this,"Please Select Students for Mark Attendance",Toast.LENGTH_LONG).show();
        }else{
            int pageWidth = 1080;

            PdfDocument attendancePDF = new PdfDocument();
            Paint pdfPaint = new Paint();

            PdfDocument.PageInfo attendancePageInfo = new PdfDocument.PageInfo.Builder(1080,1920,1).create();
            PdfDocument.Page attendancePage = attendancePDF.startPage(attendancePageInfo);
            Canvas canvas = attendancePage.getCanvas();

            //Heading of PDF
            pdfPaint.setTextAlign(Paint.Align.CENTER);
            pdfPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            pdfPaint.setTextSize(60);
            canvas.drawText("MCA Attendance List",pageWidth/2,100,pdfPaint);

            //Current Date of PDF
            pdfPaint.setTextAlign(Paint.Align.LEFT);
            pdfPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.ITALIC));
            pdfPaint.setTextSize(30);
            canvas.drawText("Date: "+todayDate,50,200,pdfPaint);

            //Subject Name
            pdfPaint.setTextAlign(Paint.Align.RIGHT);
            pdfPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            pdfPaint.setTextSize(30);
            canvas.drawText("Subject: "+"Advance Java",900,200,pdfPaint);

            //Present Students Heading
            pdfPaint.setTextAlign(Paint.Align.LEFT);
            pdfPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            pdfPaint.setTextSize(40);
            canvas.drawText("List of Present Students",100,400,pdfPaint);

            pdfPaint.setTextAlign(Paint.Align.LEFT);
            pdfPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));
            pdfPaint.setTextSize(30);

            int printno = 1;
            int printheight = 500;
            for(int i=0;i<presentStudents.size();i++){
                canvas.drawText(printno+". "+presentStudents.get(i).toString()+ "      -     Present",200,printheight,pdfPaint);
                printno++;
                printheight+=35;
            }

            attendancePDF.finishPage(attendancePage);

            File filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            String fileName = "example.pdf";
            File pdfFile = new File(filePath, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(pdfFile);
                attendancePDF.writeTo(fos);
                attendancePDF.close();
                fos.close();
                Toast.makeText(mark_attendance.this, "PDF Created Successfully", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.d("Exception Errors: ",e.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("Exception Errors: ",e.toString());
            }
        }
    }

    public void markAttendance(String datavalue) {
        dataholder2.clear();
        Cursor cursor = new database(this).getStudents(datavalue);
        if (isNull(cursor)) {
            Toast.makeText(this, "No Record Found! (Error 404)", Toast.LENGTH_SHORT).show();
        }
        while (cursor.moveToNext()) {
            model obj2 = new model(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3));
            dataholder2.add(obj2);
            adapter2 = new MyAdapter2(dataholder2, this::setCheckBoxCount);
            recyclerView2.setAdapter(adapter2);
        }
    }

    @Override
    public void setCheckBoxCount(int checkCounter) {
        if(selectAll.isChecked()){
            studentcount = dataholder2.size()+checkCounter;
            showCheckboxCount.setText(String.valueOf(dataholder2.size()+checkCounter));
        }else {
            studentcount = checkCounter;
            showCheckboxCount.setText(String.valueOf(checkCounter));
        }
    }
}