package com.example.markmyattendance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class database extends SQLiteOpenHelper {
    private static final String dbname = "MarkAttendance";
    public database(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qrycreate="create table students ( id integer primary key autoincrement, q_id text,student_name text,student_no text)";
        sqLiteDatabase.execSQL(qrycreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String qryupdate = "DROP TABLE IF EXISTS students";
        sqLiteDatabase.execSQL(qryupdate);
        onCreate(sqLiteDatabase);
    }
    public String add_student(String q_id ,String name,String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("q_id",q_id);
        c.put("student_name",name);
        c.put("student_no",phone);
        float r = db.insert("students",null,c);
        if(r==-1){
            return "no";
        }else {
            return "yes";
        }
    }
    public Cursor getStudents(String selectedValue)
    {
        String table_colname;
        if(selectedValue.equals("Q-id")){
            table_colname = "q_id";
        }else{
           table_colname = "student_name";
        }
        SQLiteDatabase db = this.getWritableDatabase();
        String qryread = "select * from students order by " + table_colname +" ASC";
        Cursor cursor = db.rawQuery(qryread,null);
        return  cursor;
    }

}
