package com.example.gui;

public class Semester extends DataBaseLayer {

    int semesterID ;
    String title;
    int capacity;
    String section;

    int creditHours;




    public String getSection() {
        return section;
    }

    public void setSession(String section) {
        this.section = section;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public Semester(int id, String title, int cap, String session, int creditHours){


        this.semesterID = id;
        this.title = title;
        this.capacity = cap;
        this.section = session;
        this.creditHours = creditHours;
    }

    public int getSemesterID() {
        return semesterID;
    }

    public void setSemesterID(int semesterID) {
        this.semesterID = semesterID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
