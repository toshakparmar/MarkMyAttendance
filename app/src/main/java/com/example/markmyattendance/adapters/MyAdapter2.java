package com.example.markmyattendance.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.markmyattendance.R;
import com.example.markmyattendance.models.model;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.holder> {

    public checkboxCount checkboxCount;
    public ArrayList<model> dataholder2;
    public ArrayList<String> markedStudents = new ArrayList<>();
    public ArrayList<String> allStudents = new ArrayList<>();
    final boolean[] studentCheckState;
    public boolean isSelectedAll = false;
    public int checkCounter = 0;

    public MyAdapter2(ArrayList<model> dataholder2, checkboxCount checkboxCount) {
        this.dataholder2 = dataholder2;
        studentCheckState = new boolean[dataholder2.size()];
        this.checkboxCount = checkboxCount;
    }

    @NonNull
    @Override
    public MyAdapter2.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_markattendance_row,parent,false);
        return new MyAdapter2.holder(view);
    }

    public void selectAllCheckbox(){
        Log.e("onClickSelectAll","yes");
        isSelectedAll = true;
        notifyDataSetChanged();
    }
    public void unselectAllCheckbox(){
        Log.e("onClickSelectAll","yes");
        isSelectedAll = false;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder,int position) {

      final int current = holder.getAbsoluteAdapterPosition();

      holder.student_name.setText((current+1)+". "+dataholder2.get(current).getStudent_name());

      holder.student_check.setChecked(false);

      allStudents.add(dataholder2.get(current).getStudent_name());

      if (isSelectedAll){
          holder.student_check.setChecked(true);

          if(studentCheckState[position]){
              holder.student_check.setChecked(false);
          }else{
              holder.student_check.setChecked(true);
          }

          holder.student_check.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if(holder.student_check.isChecked()){
                      studentCheckState[current] = false;
                      markedStudents.add(dataholder2.get(current).getStudent_name());

                  }else{
                      studentCheckState[current] = true;
                      markedStudents.remove(dataholder2.get(current).getStudent_name());
                  }
                  checkboxCount.setCheckBoxCount(markedStudents.size());
              }
          });
      }else {

          holder.student_check.setChecked(false);

          if(studentCheckState[position]){
              holder.student_check.setChecked(true);
          }else{
              holder.student_check.setChecked(false);
          }

          holder.student_check.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if(holder.student_check.isChecked()){
                      studentCheckState[current] = true;
                      markedStudents.add(dataholder2.get(current).getStudent_name());

                  }else{
                      studentCheckState[current] = false;
                      markedStudents.remove(dataholder2.get(current).getStudent_name());
                  }
                  checkboxCount.setCheckBoxCount(markedStudents.size());
              }
          });
        }
    }

    @Override
    public int getItemCount() {
        return dataholder2.size();
    }

    class holder extends RecyclerView.ViewHolder{
        TextView student_name;
        CheckBox student_check;
        public holder(View itemView){
            super(itemView);
            student_name = (TextView) itemView.findViewById(R.id.student_name_show2);
            student_check = (CheckBox) itemView.findViewById(R.id.studentCheck);
        }
    }

    public ArrayList<String> getMarkedStudentsList(){
        return markedStudents;
    }

    public ArrayList<String> getAllStudentsList(){ return allStudents; }

    public interface checkboxCount{
        void setCheckBoxCount(int checkCounter);
    }
}