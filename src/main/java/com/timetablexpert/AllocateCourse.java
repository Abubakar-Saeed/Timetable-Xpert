package com.timetablexpert;

public class AllocateCourse {


    int subjectID;
    String subjectTitle;
    String lab;

    String type;

    public AllocateCourse(int subjectID, String subjectTitle, String lab,String type) {
        this.subjectID = subjectID;
        this.subjectTitle = subjectTitle;
        this.lab = lab;
        this.type = type;
    }

    public int getSubjectID() {
        return subjectID;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }
}
