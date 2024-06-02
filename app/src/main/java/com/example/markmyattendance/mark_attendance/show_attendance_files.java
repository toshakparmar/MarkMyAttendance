package com.example.markmyattendance.mark_attendance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.markmyattendance.PDF.PDFViewer;
import com.example.markmyattendance.R;
import com.example.markmyattendance.adapters.PDFAdapter;
import com.example.markmyattendance.database;
import com.example.markmyattendance.interfaces.OnPdfDeleteListener;
import com.example.markmyattendance.interfaces.OnPdfSelectListener;
import com.example.markmyattendance.students.view_students;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class show_attendance_files extends AppCompatActivity implements OnPdfSelectListener, OnPdfDeleteListener{

    private RecyclerView pdf_files;
    private List<File> pdfList;
    public PDFAdapter pdfAdapter;
    public File allAttendancePdfFiles;
    public Button deleteYes,deleteNo;
    public TextView showNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance_files);

        pdf_files = (RecyclerView) findViewById(R.id.attendance_files);

        String foldername = "AttendanceFiles";
        allAttendancePdfFiles = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)),foldername);

        take_permission();
    }

    private void take_permission() {
        Dexter.withContext(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        displayPDF();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    public ArrayList<File> findPDF(File file){
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        for(File singleFile : files){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                arrayList.addAll(findPDF(singleFile));
            }else{
                if(singleFile.getName().endsWith(".pdf")){
                    arrayList.add(singleFile);
                }
            }
        }
        return arrayList;
    }

    public void displayPDF(){
        pdf_files.setHasFixedSize(true);
        pdf_files.setLayoutManager(new LinearLayoutManager(this));
        pdfList = new ArrayList<>();
        pdfList.addAll(findPDF(allAttendancePdfFiles));
        pdfAdapter = new PDFAdapter((Context) this, pdfList, (OnPdfSelectListener) this, (OnPdfDeleteListener) this);
        pdf_files.setAdapter(pdfAdapter);
    }

    @Override
    public void onPdfSelected(File file) {
        startActivity(new Intent(this, PDFViewer.class).putExtra("path", file.getAbsolutePath()));
    }

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    public void onPdfDeleted(File file) {

        //Delete PDF File Dialog Box
        AlertDialog.Builder deletePdfBuilder = new AlertDialog.Builder(show_attendance_files.this);
        View deletePdfView = LayoutInflater.from(show_attendance_files.this).inflate(R.layout.activity_pdf_delete,null);
        deletePdfBuilder.setCancelable(false);
        deletePdfBuilder.setView(deletePdfView);

        AlertDialog alertDialogeDeletePdf = deletePdfBuilder.create();
        alertDialogeDeletePdf.show();

        deleteYes = deletePdfView.findViewById(R.id.deletePdfYes);
        deleteNo = deletePdfView.findViewById(R.id.deletePdfNo);
        showNote = deletePdfView.findViewById(R.id.alert_show_file_name);

        showNote.setText("Do you want to delete " + file.getName() + " Pdf File");

        deleteYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDeleted = file.delete();
                if(isDeleted) { Toast.makeText(show_attendance_files.this, "PDF File Deleted Successfully!", Toast.LENGTH_LONG).show(); }
                else{ Toast.makeText(show_attendance_files.this, "PDF File didn't Delete! Please try again...", Toast.LENGTH_SHORT).show(); }
                finish();
                startActivity(getIntent());
            }
        });

        deleteNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
    }

}