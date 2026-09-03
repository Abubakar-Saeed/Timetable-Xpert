package com.timetablexpert;

public class Lab {

    String LabNo;
    int LabID;
    String Department;
    int capacity;

    public Lab(String labNo, int labID, String department, int capacity) {
        LabNo = labNo;
        LabID = labID;
        Department = department;
        this.capacity = capacity;
    }

    public String getLabNo() {
        return LabNo;
    }

    public void setLabNo(String labNo) {
        LabNo = labNo;
    }

    public int getLabID() {
        return LabID;
    }

    public void setLabID(int labID) {
        LabID = labID;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
