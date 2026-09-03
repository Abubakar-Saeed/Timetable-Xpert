package com.timetablexpert;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

public class Course {

    private int courseID;
    private String title;
    private String courseCode;
    private String creditHours;

    private String semester;
    private String program;


    public Course(){}
    public Course(int courseID, String title, String courseCode, String creditHours, String semester, String program) {

        this.courseID = courseID;
        this.title = title;
        this.courseCode = courseCode;
        this.creditHours = creditHours;
        this.semester = semester;
        this.program = program;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(String creditHours) {
        this.creditHours = creditHours;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}



