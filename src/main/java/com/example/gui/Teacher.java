package com.example.gui;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

public class Teacher extends DataBaseLayer{

    int teacherID;
    String teacherName;
   String phone;

   String gender;
   String email;

   String department;
   String type;

    // Constructor initializes professors list




    public Teacher(int ID, String name, String phone, String gender, String department, String email, String type) {

        this.teacherID = ID;
        this.teacherName = name;
        this.phone = phone;
        this.gender = gender;
        this.department = department;
        this.email = email;
        this.type = type;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail(){

        return email;
    }
    public int getTeacherID() {
        return teacherID;
    }

    public void setId(int id) {
        this.teacherID = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setName(String name) {
        this.teacherName = name;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


}
