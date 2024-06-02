package com.example.markmyattendance.PDF;


import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.markmyattendance.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PDFViewer extends AppCompatActivity {

    String filePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        PDFView pdfView = findViewById(R.id.pdfView);
        filePath = getIntent().getStringExtra("path");

        File file = new File(filePath);
        Uri path = Uri.fromFile(file);
        pdfView.fromUri(path).load();
    }
}

































//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.util.Log;
//
//import com.github.barteksc.pdfviewer.PDFView;
//import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
//import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
//import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
//import com.shockwave.pdfium.PdfDocument;
//
//import java.util.List;
//
//public class PDFViewer extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {
//
//    PDFView pdfView;
//    Integer pageNumber = 0;
//    String pdfFileName;
//    String TAG="PdfActivity";
//    int position=-1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pdfviewer);
//        init();
//    }
//
//    private void init(){
//        pdfView= (PDFView)findViewById(R.id.pdfView);
//        position = getIntent().getIntExtra("position",-1);
//        displayFromSdcard();
//    }
//
//    private void displayFromSdcard() {
////        pdfFileName = show_attendance_files.fileList.get(position).getName();
////
////        pdfView.fromFile(show_attendance_files.fileList.get(position))
////                .defaultPage(pageNumber)
////                .enableSwipe(true)
////                .swipeHorizontal(false)
////                .onPageChange(this)
////                .enableAnnotationRendering(true)
////                .onLoad(this)
////                .scrollHandle(new DefaultScrollHandle(this))
////                .load();
//    }
//    @Override
//    public void onPageChanged(int page, int pageCount) {
//        pageNumber = page;
//        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
//    }
//
//    @Override
//    public void loadComplete(int nbPages) {
//        PdfDocument.Meta meta = pdfView.getDocumentMeta();
//        printBookmarksTree(pdfView.getTableOfContents(), "-");
//
//    }
//
//    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
//        for (PdfDocument.Bookmark b : tree) {
//
//            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));
//
//            if (b.hasChildren()) {
//                printBookmarksTree(b.getChildren(), sep + "-");
//            }
//        }
//    }
//}
