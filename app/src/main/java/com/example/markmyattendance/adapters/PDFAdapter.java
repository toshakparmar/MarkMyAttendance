package com.example.markmyattendance.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.markmyattendance.interfaces.OnPdfDeleteListener;
import com.example.markmyattendance.interfaces.OnPdfSelectListener;
import com.example.markmyattendance.PDF.PDFViewHolder;
import com.example.markmyattendance.R;

import java.io.File;
import java.util.List;

public class PDFAdapter extends RecyclerView.Adapter<PDFViewHolder>{

    private Context context;
    private List<File> pdfFiles;
    private OnPdfSelectListener onPdfSelectListener;
    private OnPdfDeleteListener onPdfDeleteListener;

    public PDFAdapter(Context context, List<File> pdfFiles, OnPdfSelectListener onPdfSelectListener, OnPdfDeleteListener onPdfDeleteListener) {
        this.context = context;
        this.pdfFiles = pdfFiles;
        this.onPdfSelectListener = onPdfSelectListener;
        this.onPdfDeleteListener = onPdfDeleteListener;
    }

    @NonNull
    @Override
    public PDFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PDFViewHolder(LayoutInflater.from(context).inflate(R.layout.attendance_file_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PDFViewHolder holder, @SuppressLint("RecyclerView") int position) {
        File file = pdfFiles.get(position);
        holder.pdfFileName.setText(pdfFiles.get(position).getName());
        holder.pdfFileName.setSelected(true);
        holder.attendancePdfFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPdfSelectListener.onPdfSelected(file);
            }
        });
        holder.sharePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri pdfUri = FileProvider.getUriForFile(context,"com.example.markmyattendance.fileprovider",file);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                shareIntent.setType("application/pdf");
                shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(Intent.createChooser(shareIntent, "Share PDF With:"));
            }
        });
        holder.deletePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPdfDeleteListener.onPdfDeleted(file);
            }
        });
    }

    @Override
    public int getItemCount() { return pdfFiles.size(); }
}


