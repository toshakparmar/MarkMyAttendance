package com.example.markmyattendance.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.markmyattendance.R;
import com.example.markmyattendance.models.model;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.holder> {

    ArrayList<model> dataholder;
    private OnEditListener editListener;

    public MyAdapter(ArrayList<model> dataholder,OnEditListener onEditListener) {
        this.dataholder = dataholder;
        this.editListener = onEditListener;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_student_row,parent,false);
       return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.holder holder, int position)
    {
        holder.student_id.setText(dataholder.get(position).getStudent_id());
        holder.student_qid.setText(dataholder.get(position).getStudent_qid());
        holder.student_name.setText(dataholder.get(position).getStudent_name());
        holder.student_phone.setText(dataholder.get(position).getStudent_phone());
    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class holder extends RecyclerView.ViewHolder{
        TextView student_id, student_qid,student_name,student_phone;
        public holder(View itemView){
            super(itemView);
            student_id = (TextView) itemView.findViewById(R.id.student_id);
            student_qid =  (TextView) itemView.findViewById(R.id.student_qid_show);
            student_name = (TextView) itemView.findViewById(R.id.student_name_show);
            student_phone = (TextView) itemView.findViewById(R.id.student_phone_show);
        }
    }
    public interface OnEditListener{
        void editStudents(model currentdata);
    }
}
