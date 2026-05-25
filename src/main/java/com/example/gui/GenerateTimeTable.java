package com.example.gui;

public class GenerateTimeTable {

    int timeTableID;
    String day;
    String slot;
    String title;
    String room;
    String lab;

    public int getTimeTableID() {

        return timeTableID;

    }

    public void setTimeTableID(int timeTableID) {

        this.timeTableID = timeTableID;

    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public GenerateTimeTable(int timeTableID, String day, String slot, String title) {

        this.timeTableID = timeTableID;
        this.day = day;
        this.slot = slot;
        this.title = title;

    }
}
