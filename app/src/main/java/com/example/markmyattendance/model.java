package com.example.markmyattendance;

public class model {
    String qid,name,phone;

    public model(String student_qid, String student_name, String student_phone) {
        qid = student_qid;
        name = student_name;
        phone = student_phone;
    }

    public String getStudent_qid() {
        return qid;
    }

    public void setStudent_qid(String student_qid) {
        qid = student_qid;
    }

    public String getStudent_name() {
        return name;
    }

    public void setStudent_name(String student_name) {
        name = student_name;
    }

    public String getStudent_phone() {
        return phone;
    }

    public void setStudent_phone(String student_phone) {
        phone = student_phone;
    }
}
