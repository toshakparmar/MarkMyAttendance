package com.example.markmyattendance.PDF;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.markmyattendance.R;

public class PDFViewHolder extends RecyclerView.ViewHolder {

    public TextView pdfFileName;
    public ImageButton sharePDF, deletePDF;
    public CardView attendancePdfFile;

    public PDFViewHolder(@NonNull View itemView) {
        super(itemView);
        pdfFileName = itemView.findViewById(R.id.attendance_file_name);
        sharePDF = itemView.findViewById(R.id.share_pdf);
        deletePDF = itemView.findViewById(R.id.delete_pdf);
        attendancePdfFile = itemView.findViewById(R.id.attendance_file_card);
    }
}
